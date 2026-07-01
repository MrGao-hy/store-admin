package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.BrandQueryDTO;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.entity.Brand;
import com.gxh.admin.system.mapper.BrandMapper;
import com.gxh.admin.system.service.IBrandService;
import com.gxh.admin.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public Result<IPage<Brand>> getBrandList(BrandQueryDTO queryDTO, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.hasText(queryDTO.getName()), Brand::getName, queryDTO.getName());
        if (queryDTO.getStatus() != null) {
            wrapper.eq(Brand::getStatus, queryDTO.getStatus());
        }
        wrapper.orderByDesc(Brand::getCreateTime);

        IPage<Brand> brandPage = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        IPage<Brand> result = page(brandPage, wrapper);

        return Result.success(result, "获取品牌列表成功");
    }

    @Override
    public Result<String> addBrand(Brand brand, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        try {
            save(brand);

            return Result.success("添加品牌成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result<String> updateBrand(Brand brand, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        if (brand.getId() == null || brand.getId().isEmpty()) {
            return Result.fail("品牌ID不能为空");
        }

        LambdaQueryWrapper<Brand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Brand::getId, brand.getId());
        boolean isExist = exists(queryWrapper);
        if (!isExist) {
            return Result.fail("品牌不存在");
        }

        try {
            updateById(brand);

            return Result.success("修改品牌成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result<String> deleteBrand(String id, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        Brand brand = getById(id);
        if (brand == null) {
            return Result.fail("品牌不存在");
        }

        try {
            removeById(id);

            return Result.success("删除品牌成功");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Result<List<Brand>> getBrandOptions(HttpServletRequest request) {
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.select(Brand::getId, Brand::getName);
        wrapper.orderByDesc(Brand::getCreateTime);

        List<Brand> result = list(wrapper);

        return Result.success(result, "获取品牌选项成功");
    }

    @Override
    public Result<String> updateBrandStatus(StatusDTO statusDTO, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        Brand brand = getById(statusDTO.getId());
        if (brand == null) {
            return Result.fail("品牌不存在");
        }

        LambdaUpdateWrapper<Brand> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Brand::getId, statusDTO.getId());
        wrapper.set(Brand::getStatus, statusDTO.getStatus());

        update(wrapper);

        return Result.success("修改品牌状态成功");
    }

    @Override
    public Result<Brand> getBrandDetail(String id, HttpServletRequest request) {
        Brand brand = getById(id);
        if (brand == null) {
            return Result.fail("品牌不存在");
        }

        return Result.success(brand, "获取品牌详情成功");
    }

}