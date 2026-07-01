package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.entity.Role;
import com.gxh.admin.system.entity.Shop;
import com.gxh.admin.system.entity.User;
import com.gxh.admin.system.entity.UserShop;
import com.gxh.admin.system.mapper.ShopMapper;
import com.gxh.admin.system.mapper.UserMapper;
import com.gxh.admin.system.mapper.UserShopMapper;
import com.gxh.admin.system.service.IRoleService;
import com.gxh.admin.system.service.IShopService;
import com.gxh.admin.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gxh.admin.system.vo.MenuOptions;
import com.gxh.admin.system.vo.ShopOptionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 门店基础信息表 服务实现类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private UserShopMapper userShopMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRoleService roleService;

    @Override
    public Result<String> addShop(Shop shop, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        save(shop);
        return Result.success("创建门店成功");
    }

    @Override
    public Result<String> updateShop(Shop shop, HttpServletRequest request) {
        String userId = userRoleService.getUserIdFromRequest(request);
        boolean isShopAdmin = userRoleService.hasShopAdminRole(request);

        if (isShopAdmin) {
            LambdaQueryWrapper<UserShop> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(UserShop::getUserId, userId);
            wrapper.eq(UserShop::getShopId, shop.getId());
            long count = userShopMapper.selectCount(wrapper);
            if (count == 0) {
                return Result.fail("权限不足，只能修改本门店信息");
            }
        } else {
            Result<Void> checkResult = userRoleService.checkAdminPermission(request);
            if (checkResult != null) {
                return Result.fail(checkResult.getMessage());
            }
        }

        shop.setUpdateTime(LocalDateTime.now());
        updateById(shop);
        return Result.success("更新门店信息成功");
    }

    @Override
    public Result<String> deleteShop(String id, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        Shop shop = getById(id);
        if (shop == null) {
            return Result.fail("门店不存在");
        }

        removeById(id);
        return Result.success("删除门店成功");
    }

    @Override
    public Result<Shop> getShopDetail(String id, HttpServletRequest request) {
        String userId = userRoleService.getUserIdFromRequest(request);
        boolean isShopAdmin = userRoleService.hasShopAdminRole(request);

        Shop shop = getById(id);
        if (shop == null) {
            return Result.fail("门店不存在");
        }

        if (isShopAdmin) {
            LambdaQueryWrapper<UserShop> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(UserShop::getUserId, userId);
            wrapper.eq(UserShop::getShopId, id);
            long count = userShopMapper.selectCount(wrapper);
            if (count == 0) {
                return Result.fail("权限不足，只能查看本门店信息");
            }
        } else {
            Result<Void> checkResult = userRoleService.checkAdminPermission(request);
            if (checkResult != null) {
                return Result.fail(checkResult.getMessage());
            }
        }

        return Result.success(shop, "获取门店信息成功");
    }

    @Override
    public Result<IPage<Shop>> getShopList(String name, Integer current, Integer size, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        LambdaQueryWrapper<Shop> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.hasText(name), Shop::getName, name);
        wrapper.orderByDesc(Shop::getCreateTime);

        IPage<Shop> shopPage = new Page<>(current, size);
        IPage<Shop> result = page(shopPage, wrapper);

        return Result.success(result, "获取门店列表成功");
    }

    @Override
    public Result<IPage<User>> getShopEmployeeList(String shopId, HttpServletRequest request) {
        String userId = userRoleService.getUserIdFromRequest(request);
        boolean isShopAdmin = userRoleService.hasShopAdminRole(request);

        if (isShopAdmin) {
            LambdaQueryWrapper<UserShop> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(UserShop::getUserId, userId);
            wrapper.eq(UserShop::getShopId, shopId);
            long count = userShopMapper.selectCount(wrapper);
            if (count == 0) {
                return Result.fail("权限不足，只能查看本门店员工列表");
            }
        } else {
            Result<Void> checkResult = userRoleService.checkAdminPermission(request);
            if (checkResult != null) {
                return Result.fail(checkResult.getMessage());
            }
        }

        LambdaQueryWrapper<UserShop> userShopWrapper = Wrappers.lambdaQuery();
        userShopWrapper.eq(UserShop::getShopId, shopId);
        List<UserShop> userShopList = userShopMapper.selectList(userShopWrapper);

        if (userShopList.isEmpty()) {
            IPage<User> emptyPage = new Page<>(1, 10);
            return Result.success(emptyPage, "暂无员工");
        }

        List<String> userIds = userShopList.stream()
                .map(UserShop::getUserId)
                .collect(java.util.stream.Collectors.toList());

        LambdaQueryWrapper<User> userWrapper = Wrappers.lambdaQuery();
        userWrapper.in(User::getId, userIds);
        userWrapper.orderByDesc(User::getCreateTime);

        IPage<User> userPage = new Page<>(1, userIds.size());
        IPage<User> result = userMapper.selectPage(userPage, userWrapper);

        for (User user : result.getRecords()) {
            List<Role> roles = roleService.getUserRoles(user.getId());
            user.setRoles(roles);
        }

        return Result.success(result, "获取员工列表成功");
    }

    @Override
    public Result<String> updateShopStatus(StatusDTO statusDTO, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        Shop shop = getById(statusDTO.getId());
        if (shop == null) {
            return Result.fail("门店不存在");
        }

        shop.setStatus(statusDTO.getStatus());
        updateById(shop);

        return Result.success("更新门店状态成功");
    }

    @Override
    public Result<List<ShopOptionsVo>> getSimpleShopList(String name) {
        LambdaQueryWrapper<Shop> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Shop::getName, name).eq(Shop::getStatus, (byte) 1);

        List<Shop> shopList = list(queryWrapper);
        if (shopList == null) {
            return Result.fail("没有门店");
        } else  {
            List<ShopOptionsVo> shopOptions = shopList.stream().map(shop -> {
                ShopOptionsVo dto = new ShopOptionsVo();
                dto.setId(shop.getId());
                dto.setName(shop.getName());
                // 按需赋值其他字段
                return dto;
            }).collect(Collectors.toList());
            return Result.success(shopOptions);
        }
    }
}
