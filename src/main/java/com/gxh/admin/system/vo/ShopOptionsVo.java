package com.gxh.admin.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopOptionsVo {
    @ApiModelProperty("门店唯一ID")
    private String id;

    @ApiModelProperty("门店名称")
    private String name;
}