package com.gxh.admin.system.controller;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Role;
import com.gxh.admin.system.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @ApiOperation("获取权限列表")
    @GetMapping("list")
    public Result<List<Role>> getRoleList() {
        return roleService.getUserListService();
    }

}
