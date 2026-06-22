package com.gxh.admin.system.service;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
public interface IMenuService extends IService<Menu> {

    Result<List<Menu>> getMenuTree();

    Result<List<Menu>> getMenuList();

    Result<Menu> addMenu(Menu menu);

    Result<Menu> updateMenu(Menu menu);

    Result<Void> deleteMenu(Long id);

}
