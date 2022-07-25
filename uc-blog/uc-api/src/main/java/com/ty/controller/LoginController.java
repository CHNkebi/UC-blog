package com.ty.controller;

import com.ty.domain.http.Result;
import com.ty.domain.vo.param.LoginParam;
import com.ty.service.LoginService;
import com.ty.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam) {
        //登录 验证用户 访问用户表, 但是
        return loginService.login(loginParam);
    }


}
