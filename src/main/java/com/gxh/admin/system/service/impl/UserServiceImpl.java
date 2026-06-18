package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.LoginRequest;
import com.gxh.admin.system.entity.User;
import com.gxh.admin.system.entity.UserRole;
import com.gxh.admin.system.mapper.UserMapper;
import com.gxh.admin.system.mapper.UserRoleMapper;
import com.gxh.admin.system.service.IUserService;
import com.gxh.admin.util.JwtUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result<String> registerService(User user) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, user.getUsername());
        boolean exists = userMapper.selectCount(wrapper) > 0;
        if(exists) return Result.fail("用户已存在");

        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(md5Password);
        userMapper.insert(user);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId("3");
        userRoleService.bindUserRoleService(userRole);

        return Result.success("注册成功");
    }

    @Override
    public Result<User> loginService(LoginRequest loginRequest, HttpServletResponse response) {
//        String captchaKey = "captcha:" + loginRequest.getUuid();
//        String storedCaptcha = redisTemplate.opsForValue().get(captchaKey);
//
//        if (storedCaptcha == null) {
//            return Result.fail("验证码已过期，请重新获取");
//        }
//
//        if (!storedCaptcha.equalsIgnoreCase(loginRequest.getCode())) {
//            return Result.fail("验证码错误");
//        }

//        redisTemplate.delete(captchaKey);

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, loginRequest.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            return Result.fail("用户名不存在");
        }

        if (user.getStatus() == 0) {
            return Result.fail("账号已禁用");
        }

        String md5Password = getMd5Password(loginRequest.getPassword(), user.getSalt());
        if (!md5Password.equals(user.getPassword())) {
            return Result.fail("密码错误");
        }

                // 通过中间表查询用户角色
        LambdaQueryWrapper<UserRole> roleWrapper = Wrappers.lambdaQuery();
        roleWrapper.eq(UserRole::getUserId, user.getId());
        UserRole userRole = userRoleService.getOne(roleWrapper);

        String token = JwtUtil.generateToken(user.getId(), userRole.getRoleId());

        // 将token存入cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 24小时
        cookie.setHttpOnly(true); // 防止XSS攻击
        response.addCookie(cookie);

        return Result.success(user, "登录成功");
    }

    @Override
    public Result<User> getUserInfo(String userId) {
        User user = userMapper.selectById(userId);
        
        if (user == null) {
            return Result.fail("用户不存在");
        }


        return Result.success(user, "获取用户信息成功");
    }

    private String getMd5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

}
