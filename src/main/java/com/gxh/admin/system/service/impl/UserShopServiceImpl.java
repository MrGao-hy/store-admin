package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.entity.Shop;
import com.gxh.admin.system.entity.User;
import com.gxh.admin.system.entity.UserShop;
import com.gxh.admin.system.mapper.ShopMapper;
import com.gxh.admin.system.mapper.UserMapper;
import com.gxh.admin.system.mapper.UserShopMapper;
import com.gxh.admin.system.service.IUserRoleService;
import com.gxh.admin.system.service.IUserShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户-门店绑定表（店长、店员归属门店） 服务实现类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
@Service
public class UserShopServiceImpl extends ServiceImpl<UserShopMapper, UserShop> implements IUserShopService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private UserShopMapper userShopMapper;

    @Override
    public Result<String> bindUserShop(UserShop userShop, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkShopAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        User user = userMapper.selectById(userShop.getUserId());
        if (user == null) {
            return Result.fail("用户不存在");
        }

        Shop shop = shopMapper.selectById(userShop.getShopId());
        if (shop == null) {
            return Result.fail("门店不存在");
        }

        LambdaQueryWrapper<UserShop> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserShop::getUserId, userShop.getUserId());
        boolean existing = exists(wrapper);
        if (existing) {
            return Result.fail("用户已绑定门店，请解绑后在重新绑定");
        } else {
            try {
                save(userShop);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return Result.success("绑定门店成功");
    }

    @Override
    public Result<String> unbindUserShop(String userId, String shopId, HttpServletRequest request) {
        String currentUserId = userRoleService.getUserIdFromRequest(request);
        boolean isShopAdmin = userRoleService.hasShopAdminRole(request);

        if (!isShopAdmin) {
            return Result.fail("权限不足，只有门店店长才能解除员工绑定");
        }

        String currentUserShopId = getShopIdByUserId(currentUserId);
        if (!shopId.equals(currentUserShopId)) {
            return Result.fail("权限不足，只能解除本门店员工绑定");
        }

        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }

        LambdaQueryWrapper<UserShop> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserShop::getUserId, userId);
        wrapper.eq(UserShop::getShopId, shopId);
        int count = userShopMapper.delete(wrapper);

        if (count == 0) {
            return Result.fail("用户未绑定该门店");
        }

        return Result.success("解除绑定成功");
    }

    @Override
    public String getShopIdByUserId(String userId) {
        LambdaQueryWrapper<UserShop> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserShop::getUserId, userId);
        UserShop userShop = userShopMapper.selectOne(wrapper);
        return userShop != null ? userShop.getShopId() : null;
    }
}
