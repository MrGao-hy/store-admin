package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Role;
import com.gxh.admin.system.mapper.RoleMapper;
import com.gxh.admin.system.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxh.admin.system.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    IUserRoleService userRoleService;

    @Override
    public Result<List<Role>> getUserListService() {
        try {
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            List<Role> roles = list(wrapper);

            return Result.success(roles == null ? Collections.emptyList() : roles);
        } catch (Exception err) {
            return Result.fail("角色列表查询错误");
        }
    }

    @Override
    public List<Role> getUserRoles(String id) {
        Result<List<String>> roleIdsResult = userRoleService.getRoleIdsByUserId(id);
        List<String> roleIds = roleIdsResult.getData();
        if (roleIds != null && !roleIds.isEmpty()) {
            // 根据角色ID列表查询Role对象
            return listByIds(roleIds);
        } else {
            throw new RuntimeException("角色ID集合不能为空");
        }
    }
}
