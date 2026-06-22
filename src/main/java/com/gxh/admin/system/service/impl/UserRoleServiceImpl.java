package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxh.admin.system.entity.Role;
import com.gxh.admin.system.mapper.RoleMapper;
import org.springframework.util.CollectionUtils;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.UserRole;
import com.gxh.admin.system.mapper.UserRoleMapper;
import com.gxh.admin.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.gxh.admin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户角色关联中间表 服务实现类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Result<String> bindUserRoleService(UserRole userRole) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userRole.getUserId());

        remove(queryWrapper);
        if (userRole.getRoleIds() != null && userRole.getRoleIds().length > 0) {
            // 2. 批量查询数据库中有效的角色ID

            List<UserRole> list = new ArrayList<>();
            for (String roleId : userRole.getRoleIds()) {
                // 验证角色是否存在
                Role role = roleMapper.selectById(roleId);
                if (role == null) {
                    return Result.fail("角色ID [" + roleId + "] 不存在");
                }

                UserRole newUserRole = new UserRole();
                newUserRole.setUserId(userRole.getUserId());
                newUserRole.setRoleId(roleId);
                list.add(newUserRole);
            }
            saveBatch(list);
        } else {
            LambdaQueryWrapper<Role> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Role::getId, userRole.getRoleId());
            boolean isExist = roleMapper.exists(queryWrapper1);
            if (!isExist) {
                return Result.fail("角色ID [" + userRole.getRoleId() + "] 不存在");
            }

            save(userRole);
        }

        return Result.success("添加成功");
    }

    @Override
    public Result<List<String>> getRoleIdsByUserId(String userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        java.util.List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        java.util.List<String> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(java.util.stream.Collectors.toList());
        return Result.success(roleIds, "查询成功");
    }

    /**
     * 验证ADMIN权限
     *
     * @param request HTTP请求
     * @return 验证失败返回错误Result，成功返回null
     */
    public Result<Void> checkAdminPermission(HttpServletRequest request) {
        String token = getTokenFromCookie(request);
        if (token == null) {
            return Result.fail("未登录，请先登录");
        }

        if (!JwtUtil.validateToken(token)) {
            return Result.fail("token已过期，请重新登录");
        }

        List<String> roleCodes = JwtUtil.getRoleCodesFromToken(token);
        if (roleCodes == null || !roleCodes.contains("ADMIN")) {
            return Result.fail("权限不足，只有管理员才能操作");
        }

        return null;
    }

    /**
     * 从Cookie中获取Token
     *
     * @param request HTTP请求
     * @return Token字符串，不存在返回null
     */
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
