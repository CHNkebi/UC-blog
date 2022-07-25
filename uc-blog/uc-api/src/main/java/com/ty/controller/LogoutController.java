package com.ty.controller;

import com.ty.domain.http.Result;
import com.ty.domain.vo.param.LoginParam;
import com.ty.service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Resource
    private LoginService loginService;

    @GetMapping
    public Result login(@RequestHeader("Authorization") String token) {
        return loginService.logout(token);
    }
}
