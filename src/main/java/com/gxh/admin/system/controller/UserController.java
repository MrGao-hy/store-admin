package com.gxh.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.LoginRequest;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.dto.UserQueryDTO;
import com.gxh.admin.system.entity.User;
import com.gxh.admin.system.entity.UserRole;
import com.gxh.admin.system.service.IUserService;
import com.gxh.admin.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
    @GetMapping("detail/{id}")
    public Result<User> getUserInfo(@PathVariable String id, HttpServletRequest request) {
        String token = getTokenFromCookie(request);

        String realId = (id == null || "undefined".equals(id)) ? null : id;

        String userId = Optional.ofNullable(realId)
                .orElseGet(() -> String.valueOf(JwtUtil.getUserIdFromToken(token)));
        return userService.getUserInfo(userId, request);
    }

    @ApiOperation("获取用户列表")
    @PostMapping("list")
    public Result<IPage<User>> getUserList(@RequestBody UserQueryDTO queryDTO, HttpServletRequest request) {
        return userService.getUserList(queryDTO, request);
    }

    @ApiOperation("获取门店员工列表")
    @PostMapping("employee/list")
    public Result<IPage<User>> getShopEmployeeList(@RequestBody UserQueryDTO queryDTO, HttpServletRequest request) {
        return userService.getShopEmployeeList(queryDTO, request);
    }

    @ApiOperation("编辑用户")
    @PostMapping("update")
    public Result<String> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @ApiOperation("设置用户状态")
    @PostMapping("update/status")
    public Result<String> setUserStatus(@RequestBody StatusDTO statusDTO, HttpServletRequest request) {
        return userService.setUserStatus(statusDTO, request);
    }

    @ApiOperation("设置用户角色")
    @PostMapping("update/role")
    public Result<String> setUserRole(@RequestBody UserRole userRole, HttpServletRequest request) {
        return userService.setUserRole(userRole, request);
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
