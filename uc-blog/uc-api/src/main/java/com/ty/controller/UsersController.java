package com.ty.controller;

import com.ty.domain.http.Result;
import com.ty.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

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

    @PostMapping("modify")
    public Result modifyUser(@RequestBody Map<String, Object> info) {
        return sysUserService.modifyUser(info) ? Result.success(null) : Result.fail(200, "修改失败");
    }

}
