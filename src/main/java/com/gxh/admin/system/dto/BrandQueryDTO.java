package com.gxh.admin.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "BrandQueryDTO", description = "品牌查询请求DTO")
public class BrandQueryDTO {

    @ApiModelProperty("当前页码")
    private Integer current = 1;

    @ApiModelProperty("每页大小")
    private Integer size = 10;

    @ApiModelProperty("品牌名称（模糊搜索）")
    private String name;

    @ApiModelProperty("状态：0禁用 1启用")
    private Byte status;
}