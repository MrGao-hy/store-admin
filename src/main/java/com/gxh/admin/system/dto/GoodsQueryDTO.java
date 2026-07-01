package com.gxh.admin.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "GoodsQueryDTO", description = "商品查询请求DTO")
public class GoodsQueryDTO {

    @ApiModelProperty("当前页码")
    private Integer current = 1;

    @ApiModelProperty("每页大小")
    private Integer size = 10;

    @ApiModelProperty("商品名称（模糊搜索）")
    private String goodsName;

    @ApiModelProperty("是否上架")
    private Boolean isShopShelf;

    @ApiModelProperty("品牌id")
    private String brandId;

    @ApiModelProperty("门店id")
    private String shopId;

    @ApiModelProperty("是否查全部")
    private Boolean queryAllShop;
}