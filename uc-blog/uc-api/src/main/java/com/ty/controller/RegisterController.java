package com.ty.controller;


import com.ty.domain.http.Result;
import com.ty.domain.vo.param.LoginParam;
import com.ty.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam) {
        return loginService.register(loginParam);
    }
}
