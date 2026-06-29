package com.gxh.admin.system.service;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.UserShop;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户-门店绑定表（店长、店员归属门店） 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
public interface IUserShopService extends IService<UserShop> {

    Result<String> bindUserShop(UserShop userShop, HttpServletRequest request);

    Result<String> unbindUserShop(String userId, String shopId, HttpServletRequest request);

    String getShopIdByUserId(String userId);

}
