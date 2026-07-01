package com.gxh.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.GoodsDTO;
import com.gxh.admin.system.dto.GoodsQueryDTO;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.entity.Goods;
import com.gxh.admin.system.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "眼镜单品商品管理")
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @ApiOperation("获取商品列表（管理员/店长）")
    @PostMapping("list")
    public Result<IPage<Goods>> getGoodsList(@RequestBody GoodsQueryDTO queryDTO, HttpServletRequest request) {
        return goodsService.getGoodsList(queryDTO, request);
    }

    @ApiOperation("添加商品（管理员）")
    @PostMapping("add")
    public Result<String> addGoods(@RequestBody GoodsDTO goodsDTO, HttpServletRequest request) {
        return goodsService.addGoods(goodsDTO, request);
    }

    @ApiOperation("修改商品（管理员）")
    @PostMapping("update")
    public Result<String> updateGoods(@RequestBody GoodsDTO goodsDTO, HttpServletRequest request) {
        return goodsService.updateGoods(goodsDTO, request);
    }

    @ApiOperation("删除商品（管理员/店长）")
    @PostMapping("delete")
    public Result<String> deleteGoods(@RequestParam String id, HttpServletRequest request) {
        return goodsService.deleteGoods(id, request);
    }

    @ApiOperation("获取商品详情")
    @GetMapping("detail")
    public Result<Goods> getGoodsDetail(@RequestParam String id, HttpServletRequest request) {
        return goodsService.getGoodsDetail(id, request);
    }

    @ApiOperation("修改商品上架状态")
    @PostMapping("status/update")
    public Result<String> updateGoodsShelf(@RequestBody StatusDTO statusDTO, HttpServletRequest request) {
        return goodsService.updateGoodsShelf(statusDTO, request);
    }

}