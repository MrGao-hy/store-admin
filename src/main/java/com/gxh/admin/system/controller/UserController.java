package com.gxh.admin.system.controller;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.LoginRequest;
import com.gxh.admin.system.entity.User;
import com.gxh.admin.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户注册")
    @PostMapping("register")
    public Result<String> register(@RequestBody User user) { return userService.registerService(user); }

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result<User> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userService.loginService(loginRequest, response);
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return userService.getUserInfo(userId);
    }

}
