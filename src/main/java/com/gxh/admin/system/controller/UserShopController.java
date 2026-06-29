package com.gxh.admin.system.controller;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.UserShop;
import com.gxh.admin.system.service.IUserShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户门店绑定")
@RestController
@RequestMapping("/userShop")
public class UserShopController {

    @Autowired
    private IUserShopService userShopService;

    @ApiOperation("绑定用户到门店（管理员/店长）")
    @PostMapping("bind")
    public Result<String> bindUserShop(
            @RequestBody UserShop userShop,
            HttpServletRequest request) {
        return userShopService.bindUserShop(userShop, request);
    }

    @ApiOperation("解除用户门店绑定（店长）")
    @PostMapping("unbind")
    public Result<String> unbindUserShop(
            @RequestParam String userId,
            @RequestParam String shopId,
            HttpServletRequest request) {
        return userShopService.unbindUserShop(userId, shopId, request);
    }

}
