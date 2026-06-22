package com.gxh.admin.system.service;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
public interface IRoleService extends IService<Role> {

    Result<List<Role>> getUserListService();

    List<Role> getUserRoles(String id);

}
