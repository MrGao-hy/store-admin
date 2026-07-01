package com.gxh.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.GoodsDTO;
import com.gxh.admin.system.dto.GoodsQueryDTO;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.entity.Brand;
import com.gxh.admin.system.entity.Goods;
import com.gxh.admin.system.entity.GoodsShop;
import com.gxh.admin.system.mapper.BrandMapper;
import com.gxh.admin.system.mapper.GoodsMapper;
import com.gxh.admin.system.mapper.GoodsShopMapper;
import com.gxh.admin.system.service.IGoodsService;
import com.gxh.admin.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private GoodsShopMapper goodsShopMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Result<IPage<Goods>> getGoodsList(GoodsQueryDTO queryDTO, HttpServletRequest request) {
        String shopId = userRoleService.getShopIdFromRequest(request);
        if (!StringUtils.hasText(shopId)) {
            return Result.fail("门店ID不存在，请检查登录状态");
        }
        queryDTO.setShopId(shopId);
        IPage<Goods> goodsPage = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        IPage<Goods> result = baseMapper.selectGoodsShopPage(goodsPage, queryDTO);

        for (Goods goods : result.getRecords()) {
            // 填充品牌信息
            LambdaQueryWrapper<Brand> brandWrapper = new LambdaQueryWrapper<>();
            brandWrapper.eq(Brand::getId, goods.getBrandId());
            Brand brand = brandMapper.selectOne(brandWrapper);
            if (brand != null) {
                goods.setBrand(brand);
            }
        }

        return Result.success(result, "获取商品列表成功");
    }

    private void fillGoodsExtraInfo(Goods goods, String shopId) {
        LambdaQueryWrapper<GoodsShop> goodsShopWrapper = new LambdaQueryWrapper<>();
        goodsShopWrapper.eq(GoodsShop::getShopId, shopId);
        goodsShopWrapper.eq(GoodsShop::getGoodsId, goods.getId());
        GoodsShop goodsShop = goodsShopMapper.selectOne(goodsShopWrapper);

        LambdaQueryWrapper<Brand> brandWrapper = new LambdaQueryWrapper<>();
        brandWrapper.eq(Brand::getId, goods.getBrandId());
        Brand brand = brandMapper.selectOne(brandWrapper);

        if (goodsShop != null) {
            goods.setShopSalePrice(goodsShop.getShopSalePrice());
            goods.setShopStock(goodsShop.getShopStock());
            goods.setIsShopShelf(goodsShop.getIsShopShelf());
        }
        if (brand != null) {
            goods.setBrand(brand);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> addGoods(GoodsDTO goodsDTO, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkShopAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        save(goodsDTO);

        String shopId = userRoleService.getShopIdFromRequest(request);
        LambdaQueryWrapper<GoodsShop> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsShop::getShopId, shopId).eq(GoodsShop::getGoodsId, goodsDTO.getId());
        boolean isExist = goodsShopMapper.exists(wrapper);
        if (StringUtils.hasText(shopId) && !isExist) {
            GoodsShop goodsShop = new GoodsShop();
            goodsShop.setGoodsId(goodsDTO.getId());
            goodsShop.setShopId(shopId);
            goodsShop.setShopStock(goodsDTO.getShopStock() != null ? goodsDTO.getShopStock() : 0);
            goodsShop.setShopSalePrice(goodsDTO.getShopSalePrice());
            goodsShop.setIsShopShelf(goodsDTO.getIsShopShelf());

            goodsShopMapper.insert(goodsShop);
        }

        return Result.success("添加商品成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> updateGoods(GoodsDTO goodsDTO, HttpServletRequest request) {

        updateById(goodsDTO);

        String shopId = userRoleService.getShopIdFromRequest(request);
        LambdaQueryWrapper<GoodsShop> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsShop::getShopId, shopId).eq(GoodsShop::getGoodsId, goodsDTO.getId());
        GoodsShop goodsShopOne = goodsShopMapper.selectOne(wrapper);

        GoodsShop goodsShop = new GoodsShop();
        goodsShop.setGoodsId(goodsDTO.getId());
        goodsShop.setShopId(shopId);
        goodsShop.setShopStock(goodsDTO.getShopStock());
        goodsShop.setShopSalePrice(goodsDTO.getShopSalePrice());
        goodsShop.setIsShopShelf(goodsDTO.getIsShopShelf());
        if (goodsShopOne != null) {
            goodsShop.setId(goodsShopOne.getId());
            goodsShopMapper.updateById(goodsShop);
        } else {
            goodsShopMapper.insert(goodsShop);
        }

        return Result.success("修改商品成功");
    }

    @Override
    public Result<String> deleteGoods(String id, HttpServletRequest request) {
        Result<Void> checkResult = userRoleService.checkShopAdminPermission(request);
        if (checkResult != null) {
            return Result.fail(checkResult.getMessage());
        }

        Goods goods = getById(id);
        if (goods == null) {
            return Result.fail("商品不存在");
        }

        removeById(id);

        LambdaQueryWrapper<GoodsShop> goodsShopWrapper = Wrappers.lambdaQuery();
        goodsShopWrapper.eq(GoodsShop::getGoodsId, id);
        goodsShopMapper.delete(goodsShopWrapper);

        return Result.success("删除商品成功");
    }

    @Override
    public Result<Goods> getGoodsDetail(String id, HttpServletRequest request) {
        Goods goods = getById(id);
        if (goods == null) {
            return Result.fail("商品不存在");
        }

        String shopId = userRoleService.getShopIdFromRequest(request);
        if (StringUtils.hasText(shopId)) {
            fillGoodsExtraInfo(goods, shopId);
        }

        return Result.success(goods, "获取商品详情成功");
    }

    @Override
    public Result<String> updateGoodsShelf(StatusDTO statusDTO, HttpServletRequest request) {
        Goods goods = getById(statusDTO.getId());
        if (goods == null) {
            return Result.fail("商品不存在");
        }

        String shopId = userRoleService.getShopIdFromRequest(request);
        if (!StringUtils.hasText(shopId)) {
            return Result.fail("门店ID不存在");
        }

        LambdaQueryWrapper<GoodsShop> goodsShopWrapper = Wrappers.lambdaQuery();
        goodsShopWrapper.eq(GoodsShop::getGoodsId, statusDTO.getId());
        goodsShopWrapper.eq(GoodsShop::getShopId, shopId);
        GoodsShop goodsShop = goodsShopMapper.selectOne(goodsShopWrapper);

        if (goodsShop == null) {
            return Result.fail("该门店未关联此商品");
        }

        LambdaUpdateWrapper<GoodsShop> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(GoodsShop::getId, goodsShop.getId());
        updateWrapper.set(GoodsShop::getIsShopShelf, statusDTO.getStatus());
        updateWrapper.set(GoodsShop::getUpdateTime, LocalDateTime.now());
        goodsShopMapper.update(null, updateWrapper);

        return Result.success("修改上架状态成功");
    }
}