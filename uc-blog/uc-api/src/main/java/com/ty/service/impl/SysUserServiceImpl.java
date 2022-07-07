package com.ty.service.impl;

import com.ty.dao.SysUserMapper;
import com.ty.domain.pojo.SysUser;
import com.ty.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser =  sysUserMapper.selectById(id);
        if(Objects.isNull(sysUser)) {
            sysUser = new SysUser();
            sysUser.setNickname("佚名");
        }
        return sysUser;
    }
}
