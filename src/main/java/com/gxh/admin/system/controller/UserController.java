package com.gxh.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.LoginRequest;
import com.gxh.admin.system.dto.UserQueryDTO;
import com.gxh.admin.system.dto.UserStatusDTO;
import com.gxh.admin.system.entity.User;
import com.gxh.admin.system.entity.UserRole;
import com.gxh.admin.system.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户注册")
    @PostMapping("register")
    public Result<String> register(@RequestBody User user) {
        return userService.registerService(user);
    }

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result<User> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userService.loginService(loginRequest, response);
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("detail")
    public Result<User> getUserInfo(@RequestParam String id, HttpServletRequest request) {
        // 1. 优先取前端传入id，为空再取登录userId
        String userId = Optional.ofNullable(id)
                .orElse((String) request.getAttribute("userId"));
        return userService.getUserInfo(userId);
    }

    @ApiOperation("获取用户列表")
    @PostMapping("list")
    public Result<IPage<User>> getUserList(@RequestBody UserQueryDTO queryDTO, HttpServletRequest request) {
        return userService.getUserList(queryDTO, request);
    }

    @ApiOperation("设置用户状态")
    @PostMapping("update/status")
    public Result<String> setUserStatus(@RequestBody UserStatusDTO userStatusDTO, HttpServletRequest request) {
        return userService.setUserStatus(userStatusDTO, request);
    }

    @ApiOperation("设置用户角色")
    @PostMapping("update/role")
    public Result<String> setUserRole(@RequestBody UserRole userRole, HttpServletRequest request) {
        return userService.setUserRole(userRole, request);
    }

}
