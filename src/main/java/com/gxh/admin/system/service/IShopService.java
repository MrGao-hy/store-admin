package com.gxh.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Shop;
import com.gxh.admin.system.entity.User;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gxh.admin.system.vo.ShopOptionsVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 门店基础信息表 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
public interface IShopService extends IService<Shop> {

    Result<String> addShop(Shop shop, HttpServletRequest request);

    Result<String> updateShop(Shop shop, HttpServletRequest request);

    Result<String> deleteShop(String id, HttpServletRequest request);

    Result<Shop> getShopDetail(String id, HttpServletRequest request);

    Result<IPage<Shop>> getShopList(String name, Integer current, Integer size, HttpServletRequest request);

    Result<IPage<User>> getShopEmployeeList(String shopId, HttpServletRequest request);

    Result<String> updateShopStatus(String id, Boolean status, HttpServletRequest request);

    Result<List<ShopOptionsVo>> getSimpleShopList(String name);

}
