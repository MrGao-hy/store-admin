package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gxh.admin.common.jackson.BooleanToByteDeserializer;
import com.gxh.admin.common.jackson.ByteToBooleanSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商品-门店售卖关联表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-30
 */
@Data
@TableName("sys_goods_shop")
@ApiModel(value = "GoodsShop对象", description = "商品-门店售卖关联表")
public class GoodsShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键雪花ID/UUID")
    private String id;

    @ApiModelProperty("眼镜商品ID，关联glasses_goods.id")
    private String goodsId;

    @ApiModelProperty("门店ID")
    private String shopId;

    @ApiModelProperty("该门店单独库存")
    private Integer shopStock;

    @ApiModelProperty("门店专属售价（可选）")
    private BigDecimal shopSalePrice;

    @ApiModelProperty("门店内是否上架：0下架 1上架")
    @JsonSerialize(using = ByteToBooleanSerializer.class)
    @JsonDeserialize(using = BooleanToByteDeserializer.class)
    private Byte isShopShelf;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("跟新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
