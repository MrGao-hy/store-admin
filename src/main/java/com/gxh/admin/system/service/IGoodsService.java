package com.gxh.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.GoodsDTO;
import com.gxh.admin.system.dto.GoodsQueryDTO;
import com.gxh.admin.system.dto.StatusDTO;
import com.gxh.admin.system.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 眼镜单品商品表 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-30
 */
public interface IGoodsService extends IService<Goods> {

    Result<IPage<Goods>> getGoodsList(GoodsQueryDTO queryDTO, HttpServletRequest request);

    Result<String> addGoods(GoodsDTO goodsDTO, HttpServletRequest request);

    Result<String> updateGoods(GoodsDTO goodsDTO, HttpServletRequest request);

    Result<String> deleteGoods(String id, HttpServletRequest request);

    Result<Goods> getGoodsDetail(String id, HttpServletRequest request);

    Result<String> updateGoodsShelf(StatusDTO statusDTO, HttpServletRequest request);
}
