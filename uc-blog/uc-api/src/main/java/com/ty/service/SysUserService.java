package com.ty.service;

import com.ty.domain.http.Result;
import com.ty.domain.pojo.SysUser;

public interface SysUserService {

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    SysUser findUserById(Long id);

    /**
     * 根据账户密码查询用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);
}
