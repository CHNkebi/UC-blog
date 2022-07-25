package com.ty.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ty.domain.http.Result;
import com.ty.domain.pojo.SysUser;
import com.ty.domain.vo.ErrorCode;
import com.ty.domain.vo.param.LoginParam;
import com.ty.service.LoginService;
import com.ty.service.SysUserService;
import com.ty.utils.JWTUtils;
import com.ty.utils.RedisCache;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    //加密盐
    private static final String slat = "tyuc!@#$%";

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisCache redisCache;


    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去user表中查询 是否存在
         * 3.如果不存在 登录失败
         * 4.如果不存在， 使用jwt生成token 返回给前端
         * 5.token放入redis当中， redis token：user信息 设置过期时间
         * （登录认证的时候 先认证token是否合法再去redis认证）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password))
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "登录参数错误");
        password = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account, password);
        if(Objects.isNull(sysUser))
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());

        String token = JWTUtils.createToken(sysUser.getId());
        redisCache.setCacheObject("TOKEN_"+token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);

        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token))
            return null;
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(Objects.isNull(stringObjectMap))
            return null;
        String userJson =  redisCache.getCacheObject("TOKEN_" + token);
        if(StringUtils.isBlank(userJson))
            return null;

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisCache.deleteObject("TOKEN_"+token);
        return Result.success(null);
    }
}
