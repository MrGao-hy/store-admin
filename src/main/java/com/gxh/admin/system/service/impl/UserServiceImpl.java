package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.LoginRequest;
import com.gxh.admin.system.dto.UserQueryDTO;
import com.gxh.admin.system.dto.UserStatusDTO;
import com.gxh.admin.system.entity.Role;
import com.gxh.admin.system.entity.User;
import com.gxh.admin.system.entity.UserRole;
import com.gxh.admin.system.mapper.RoleMapper;
import com.gxh.admin.system.mapper.UserMapper;
import com.gxh.admin.system.mapper.UserRoleMapper;
import com.gxh.admin.system.service.IRoleService;
import com.gxh.admin.system.service.IUserRoleService;
import com.gxh.admin.system.service.IUserService;
import com.gxh.admin.util.JwtUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result<String> registerService(User user) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, user.getUsername());
        boolean exists = userMapper.selectCount(wrapper) > 0;
        if (exists)
            return Result.fail("用户已存在");

        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(md5Password);
        userMapper.insert(user);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId("3");
        userRoleMapper.insert(userRole);

        return Result.success("注册成功");
    }

    @Override
    public Result<User> loginService(LoginRequest loginRequest, HttpServletResponse response) {
        // 校验验证码
        String captchaKey = "captcha:" + loginRequest.getUuid();
        String storedCaptcha = redisTemplate.opsForValue().get(captchaKey);
        if (storedCaptcha == null) {
            return Result.fail("验证码已过期，请重新获取");
        }
        if (!storedCaptcha.equalsIgnoreCase(loginRequest.getCode())) {
            return Result.fail("验证码错误");
        }
        redisTemplate.delete(captchaKey);

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

        // 通过getRoleIdsByUserId查询角色ID列表
        Result<List<String>> roleIdsResult = userRoleService.getRoleIdsByUserId(user.getId());
        List<String> roleIds = roleIdsResult.getData();

        // 根据角色ID列表查询角色编码
        List<String> roleCodes = new ArrayList<>();
        if (roleIds != null && !roleIds.isEmpty()) {
            List<Role> roles = roleMapper.selectBatchIds(roleIds);
            roleCodes = roles.stream()
                    .map(Role::getRoleCode)
                    .collect(Collectors.toList());
        }

        String token = JwtUtil.generateToken(user.getId(), roleCodes);

        // 将token存入cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(10 * 24 * 60 * 60); // 10天
        cookie.setHttpOnly(true); // 防止XSS攻击
        response.addCookie(cookie);

        return Result.success(user, "登录成功");
    }

    @Override
    public Result<User> getUserInfo(String userId, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        User user = getById(userId);

        if (user == null) {
            return Result.fail("用户不存在");
        }

        // 调用getRoleIdsByUserId获取角色ID数组

        List<Role> roles =  roleService.getUserRoles(user.getId());
        user.setRoles(roles);

        return Result.success(user, "获取用户信息成功");
    }

    @Override
    public Result<IPage<User>> getUserList(UserQueryDTO queryDTO, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        // 构建查询条件
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.hasText(queryDTO.getUsername()), User::getUsername, queryDTO.getUsername());
        if (queryDTO.getStatus() != null) {
            wrapper.eq(User::getStatus, queryDTO.getStatus());
        }
        wrapper.orderByDesc(User::getCreateTime);

        // 分页查询
        IPage<User> userList = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        IPage<User> userPage = page(userList, wrapper);

        // 循环用户列表，查询每个用户的角色列表
        for (User user : userPage.getRecords()) {
            // 调用getRoleIdsByUserId获取角色ID数组
            List<Role> roles =  roleService.getUserRoles(user.getId());
            user.setRoles(roles);
        }

        return Result.success(userPage, "获取用户列表成功");
    }

    @Override
    public Result<String> setUserStatus(UserStatusDTO userStatusDTO, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }
        // 验证用户id是否存在
        boolean isExist = isExistUser(userStatusDTO.getUserId());
        if (!isExist) return Result.fail("用户不存在");

        LambdaUpdateWrapper<User> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(User::getId, userStatusDTO.getUserId());
        wrapper.set(User::getStatus, userStatusDTO.getStatus());
        update(wrapper);

        return Result.success(userStatusDTO.getStatus() == 1 ? "启用用户成功" : "禁用用户成功");
    }

    @Override
    public Result<String> setUserRole(UserRole userRole, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        // 验证用户id是否存在
        boolean isExist = isExistUser(userRole.getUserId());
        if (!isExist) return Result.fail("用户不存在");

        // 绑定用户角色
        Result<String> bindResult = userRoleService.bindUserRoleService(userRole);
        if (bindResult.getCode() != 20000) {
            return Result.fail(bindResult.getMessage());
        }

        return Result.success("设置用户角色成功");
    }

    public Boolean isExistUser(String id) {
        // 验证用户是否存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        return exists(wrapper);
    }

    private String getMd5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

}
