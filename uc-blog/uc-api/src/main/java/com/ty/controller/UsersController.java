package com.ty.controller;

import com.ty.domain.http.Result;
import com.ty.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("users")
public class UsersController {

    @Resource
    private SysUserService sysUserService;

    // /users/currentUser
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);
    }

}
