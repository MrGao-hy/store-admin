package com.gxh.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gxh.admin.system.dto.GoodsQueryDTO;
import com.gxh.admin.system.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 眼镜单品商品表 Mapper 接口
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-30
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<Goods> selectGoodsShopPage(IPage<Goods> page, @Param("query") GoodsQueryDTO query);
}
