package com.gxh.admin.system.controller;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Menu;
import com.gxh.admin.system.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("获取菜单树形结构")
    @GetMapping("tree")
    public Result<List<Menu>> getMenuTree(HttpServletRequest request) {
        return menuService.getMenuTree(request);
    }

    @ApiOperation("获取菜单列表")
    @GetMapping("options")
    public Result<List<Menu>> getMenuList() {
        return menuService.getMenuList();
    }

    @ApiOperation("添加菜单")
    @PostMapping("add")
    public Result<Menu> addMenu(@RequestBody Menu menu, HttpServletRequest request) {
        return menuService.addMenu(menu, request);
    }

    @ApiOperation("修改菜单")
    @PostMapping("update")
    public Result<Menu> updateMenu(@RequestBody Menu menu, HttpServletRequest request) {
        return menuService.updateMenu(menu, request);
    }

    @ApiOperation("删除菜单")
    @PostMapping("delete")
    public Result<String> deleteMenu(@RequestBody String id, HttpServletRequest request) {
        return menuService.deleteMenu(id, request);
    }

    @ApiOperation("根据ID获取菜单")
    @PostMapping("detail")
    public Result<Menu> getMenuById(@RequestBody String id, HttpServletRequest request) {
        return menuService.getMenuByIdService(id, request);
    }

}
