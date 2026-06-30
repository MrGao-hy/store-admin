package com.gxh.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.BrandQueryDTO;
import com.gxh.admin.system.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 眼镜品牌字典表 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-30
 */
public interface IBrandService extends IService<Brand> {

    Result<IPage<Brand>> getBrandList(BrandQueryDTO queryDTO, HttpServletRequest request);

    Result<String> addBrand(Brand brand, HttpServletRequest request);

    Result<String> updateBrand(Brand brand, HttpServletRequest request);

    Result<String> deleteBrand(String id, HttpServletRequest request);

    Result<List<Brand>> getBrandOptions(HttpServletRequest request);

    Result<String> updateBrandStatus(String id, Boolean status, HttpServletRequest request);

    Result<Brand> getBrandDetail(String id, HttpServletRequest request);

}
