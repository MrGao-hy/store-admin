package com.gxh.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.BrandQueryDTO;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.entity.Brand;
import com.gxh.admin.system.service.IBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "眼镜品牌管理")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @ApiOperation("获取品牌列表（管理员）")
    @PostMapping("list")
    public Result<IPage<Brand>> getBrandList(@RequestBody BrandQueryDTO queryDTO, HttpServletRequest request) {
        return brandService.getBrandList(queryDTO, request);
    }

    @ApiOperation("添加品牌（管理员）")
    @PostMapping("add")
    public Result<String> addBrand(@RequestBody Brand brand, HttpServletRequest request) {
        return brandService.addBrand(brand, request);
    }

    @ApiOperation("修改品牌（管理员）")
    @PostMapping("update")
    public Result<String> updateBrand(@RequestBody Brand brand, HttpServletRequest request) {
        return brandService.updateBrand(brand, request);
    }

    @ApiOperation("删除品牌（管理员）")
    @PostMapping("delete")
    public Result<String> deleteBrand(@RequestParam String id, HttpServletRequest request) {
        return brandService.deleteBrand(id, request);
    }

    @ApiOperation("获取品牌选项（仅返回id和名称）")
    @GetMapping("options")
    public Result<List<Brand>> getBrandOptions(HttpServletRequest request) {
        return brandService.getBrandOptions(request);
    }

    @ApiOperation("修改品牌状态（管理员）")
    @PostMapping("status/update")
    public Result<String> updateBrandStatus(@RequestBody StatusDTO statusDTO,
                                            HttpServletRequest request) {
        return brandService.updateBrandStatus(statusDTO, request);
    }

    @ApiOperation("获取品牌详情")
    @GetMapping("detail")
    public Result<Brand> getBrandDetail(@RequestParam String id, HttpServletRequest request) {
        return brandService.getBrandDetail(id, request);
    }

}