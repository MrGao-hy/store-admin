package com.gxh.admin.system.controller;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Menu;
import com.gxh.admin.system.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("获取菜单树形结构")
    @GetMapping("tree")
    public Result<List<Menu>> getMenuTree() {
        return menuService.getMenuTree();
    }

    @ApiOperation("获取菜单列表")
    @GetMapping("options")
    public Result<List<Menu>> getMenuList() {
        return menuService.getMenuList();
    }

    @ApiOperation("添加菜单")
    @PostMapping("add")
    public Result<Menu> addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result<Menu> updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("delete/{id}")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        return menuService.deleteMenu(id);
    }

    @ApiOperation("根据ID获取菜单")
    @GetMapping("{id}")
    public Result<Menu> getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        if (menu != null) {
            return Result.success(menu, "获取菜单成功");
        }
        return Result.fail("菜单不存在");
    }

}
