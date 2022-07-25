package com.ty.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ty.dao.SysUserMapper;
import com.ty.domain.http.Result;
import com.ty.domain.pojo.SysUser;
import com.ty.domain.vo.ErrorCode;
import com.ty.domain.vo.LoginUserVo;
import com.ty.service.LoginService;
import com.ty.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser =  sysUserMapper.selectById(id);
        if(Objects.isNull(sysUser)) {
            sysUser = new SysUser();
            sysUser.setNickname("佚名");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1.token合法性验证
         *   是否为空
         *   解析是否成功
         *   redis是否存在
         * 2.如果校验失败 返回错误
         * 3.如果校验成功 返回结果 LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if(Objects.isNull(sysUser))
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());

        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());

        return Result.success(loginUserVo);
    }
}
