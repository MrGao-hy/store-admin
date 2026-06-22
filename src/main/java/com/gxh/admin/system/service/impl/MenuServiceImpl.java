package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Menu;
import com.gxh.admin.system.mapper.MenuMapper;
import com.gxh.admin.system.service.IMenuService;
import com.gxh.admin.system.vo.MenuVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Result<List<MenuVO>> getMenuTree() {
        List<Menu> menus = list();
        List<MenuVO> menuTree = buildMenuTree(menus);
        return Result.success(menuTree, "获取菜单树成功");
    }

    @Override
    public Result<List<MenuVO>> getMenuList() {
        List<Menu> menus = list(
                Wrappers.<Menu>lambdaQuery()
                        .orderByAsc(Menu::getSort)
        );
        List<MenuVO> menuVOS = menus.stream().map(menu -> {
            MenuVO vo = new MenuVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());
        return Result.success(menuVOS, "获取菜单列表成功");
    }

    @Override
    @Transactional
    public Result<Menu> addMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getSort() == null) {
            menu.setSort(0);
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
    @Transactional
    public Result<Menu> updateMenu(Menu menu) {
        updateById(menu);
        return Result.success(menu, "修改菜单成功");
    }

    @Override
    @Transactional
    public Result<Void> deleteMenu(Long id) {
        // 查询是否有子菜单
        List<Menu> children = list(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, id));
        if (children != null && !children.isEmpty()) {
            return Result.fail("该菜单存在子菜单，请先删除子菜单");
        }
        removeById(id);
        return Result.success(null, "删除菜单成功");
    }

    /**
     * 构建菜单树
     */
    private List<MenuVO> buildMenuTree(List<Menu> menus) {
        List<MenuVO> result = new ArrayList<>();
        List<MenuVO> allMenus = menus.stream().map(menu -> {
            MenuVO vo = new MenuVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());

        // 按parentId分组
        List<MenuVO> rootMenus = allMenus.stream()
                .filter(menu -> menu.getParentId() == 0 || menu.getParentId() == null)
                .collect(Collectors.toList());

        for (MenuVO rootMenu : rootMenus) {
            buildChildren(rootMenu, allMenus);
            result.add(rootMenu);
        }

        return result;
    }

    /**
     * 递归构建子菜单
     */
    private void buildChildren(MenuVO parentMenu, List<MenuVO> allMenus) {
        List<MenuVO> children = allMenus.stream()
                .filter(menu -> menu.getParentId() != null && menu.getParentId().equals(parentMenu.getId()))
                .collect(Collectors.toList());

        for (MenuVO child : children) {
            buildChildren(child, allMenus);
        }

        parentMenu.setChildren(children);
    }

}
