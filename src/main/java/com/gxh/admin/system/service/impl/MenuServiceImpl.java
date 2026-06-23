package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Menu;
import com.gxh.admin.system.mapper.MenuMapper;
import com.gxh.admin.system.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxh.admin.system.service.IUserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public Result<List<Menu>> getMenuTree(HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }
        List<Menu> menus = list();
        List<Menu> menuTree = buildMenuTree(menus);
        return Result.success(menuTree, "获取菜单树成功");
    }

    @Override
    public Result<List<Menu>> getMenuList() {
        LambdaQueryWrapper<Menu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Menu::getSort);
        List<Menu> menus = list(queryWrapper);
        List<Menu> MenuS = menus.stream().map(menu -> {
            Menu vo = new Menu();
            vo.setId(menu.getId());
            vo.setName(menu.getName());
            vo.setParentId(menu.getParentId());
            vo.setChildren(menu.getChildren());
            // 不set的字段：createTime、updateTime、deleteFlag等不会赋值，前端无数据
            return vo;
        }).collect(Collectors.toList());
        List<Menu> menuTree = buildMenuTree(MenuS);
        return Result.success(menuTree);
    }

    @Override
    public Result<Menu> addMenu(Menu menu, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }
        if (menu.getVisible() == null) {
            menu.setVisible((byte) 1);
        }
        if (menu.getKeepAlive() == null) {
            menu.setKeepAlive((byte) 0);
        }
        save(menu);
        return Result.success(menu, "添加菜单成功");
    }

    @Override
    public Result<Menu> updateMenu(Menu menu, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }
        updateById(menu);
        return Result.success(menu, "修改菜单成功");
    }

    @Override
    public Result<String> deleteMenu(String id, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }
        // 查询是否有子菜单
        List<Menu> children = list(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, id));
        if (children != null && !children.isEmpty()) {
            return Result.fail("该菜单存在子菜单，请先删除子菜单");
        }
        removeById(id);
        return Result.success("删除菜单成功");
    }

    @Override
    public Result<Menu> getMenuByIdService(String id, HttpServletRequest request) {
        // 验证ADMIN权限
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }
        try {
            Menu menu = getById(id);
            if (menu != null) {
                return Result.success(menu, "获取菜单成功");
            } else {
                return Result.fail("菜单不存在");
            }
        } catch (Exception e) {
            return Result.fail("获取菜单错误");
        }
    }

    /**
     * 构建菜单树
     */
    private List<Menu> buildMenuTree(List<Menu> menus) {
        List<Menu> result = new ArrayList<>();
        List<Menu> allMenus = menus.stream().map(menu -> {
            Menu vo = new Menu();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());

        // 按parentId分组
        List<Menu> rootMenus = allMenus.stream()
                .filter(menu -> menu.getParentId().isEmpty())
                .collect(Collectors.toList());

        for (Menu rootMenu : rootMenus) {
            buildChildren(rootMenu, allMenus);
            result.add(rootMenu);
        }

        return result;
    }

    /**
     * 递归构建子菜单
     */
    private void buildChildren(Menu parentMenu, List<Menu> allMenus) {
        List<Menu> children = allMenus.stream()
                .filter(menu -> menu.getParentId() != null && menu.getParentId().equals(parentMenu.getId()))
                .collect(Collectors.toList());

        for (Menu child : children) {
            buildChildren(child, allMenus);
        }

        if (!children.isEmpty()) {
            parentMenu.setChildren(children);
        }
    }

}
