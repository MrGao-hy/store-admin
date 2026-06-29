package com.gxh.admin.system.service;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.UserRole;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户角色关联中间表 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
public interface IUserRoleService extends IService<UserRole> {

    Result<String> bindUserRoleService(UserRole userRole);

    Result<List<String>> getRoleIdsByUserId(String userId);

    Result<Void> checkAdminPermission(HttpServletRequest request);

    Result<Void> checkShopAdminPermission(HttpServletRequest request);

    boolean hasShopAdminRole(HttpServletRequest request);

    boolean hasShopRole(HttpServletRequest request);

    String getUserIdFromRequest(HttpServletRequest request);

    String getShopIdFromRequest(HttpServletRequest request);

}
