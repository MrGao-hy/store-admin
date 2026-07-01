package com.gxh.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.entity.Shop;
import com.gxh.admin.system.entity.User;

import java.util.List;
import com.gxh.admin.system.service.IShopService;
import com.gxh.admin.system.vo.ShopOptionsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "门店管理")
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private IShopService shopService;

    @ApiOperation("新增门店（管理员）")
    @PostMapping("add")
    public Result<String> addShop(@RequestBody Shop shop, HttpServletRequest request) {
        return shopService.addShop(shop, request);
    }

    @ApiOperation("更新门店（管理员/店长）")
    @PostMapping("update")
    public Result<String> updateShop(@RequestBody Shop shop, HttpServletRequest request) {
        return shopService.updateShop(shop, request);
    }

    @ApiOperation("删除门店（管理员）")
    @PostMapping("delete")
    public Result<String> deleteShop(@RequestParam String id, HttpServletRequest request) {
        return shopService.deleteShop(id, request);
    }

    @ApiOperation("获取门店详情（管理员/店长）")
    @GetMapping("detail")
    public Result<Shop> getShopDetail(@RequestParam String id, HttpServletRequest request) {
        return shopService.getShopDetail(id, request);
    }

    @ApiOperation("获取门店列表（管理员）")
    @GetMapping("list")
    public Result<IPage<Shop>> getShopList(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request) {
        return shopService.getShopList(name, current, size, request);
    }

    @ApiOperation("获取门店员工列表（管理员/店长）")
    @GetMapping("employee/list")
    public Result<IPage<User>> getShopEmployeeList(@RequestParam String shopId, HttpServletRequest request) {
        return shopService.getShopEmployeeList(shopId, request);
    }

    @ApiOperation("更新门店状态（管理员）")
    @PostMapping("status/update")
    public Result<String> updateShopStatus(
            @RequestBody StatusDTO statusDTO,
            HttpServletRequest request) {
        return shopService.updateShopStatus(statusDTO, request);
    }

    @ApiOperation("获取选择门店")
    @GetMapping("options")
    public Result<List<ShopOptionsVo>> getSimpleShopList(@RequestParam(value = "name", required = false) String name) {
        return shopService.getSimpleShopList(name);
    }

}
