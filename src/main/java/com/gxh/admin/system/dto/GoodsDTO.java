package com.gxh.admin.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gxh.admin.common.jackson.BooleanToByteDeserializer;
import com.gxh.admin.common.jackson.ByteToBooleanSerializer;
import com.gxh.admin.system.entity.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "GoodsDTO", description = "商品增改请求DTO")
public class GoodsDTO extends Goods  {

    @ApiModelProperty("眼镜商品ID，关联glasses_goods.id")
    private String goodsId;

    @ApiModelProperty("门店ID")
    private String shopId;

    @ApiModelProperty("门店专属售价")
    private BigDecimal shopSalePrice;

    @ApiModelProperty("门店单独库存")
    private Integer shopStock;

    @ApiModelProperty("门店内是否上架：0下架 1上架")
    @JsonSerialize(using = ByteToBooleanSerializer.class)
    @JsonDeserialize(using = BooleanToByteDeserializer.class)
    private Byte isShopShelf;
}