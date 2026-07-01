package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 眼镜单品商品表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-30
 */
@Data
@TableName("sys_goods")
@ApiModel(value = "Goods对象", description = "眼镜单品商品表")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品主键ID")
    private String id;

    @ApiModelProperty("商品名称（如：钛合金近视镜框）")
    private String goodsName;

    @ApiModelProperty("商品编码（唯一）")
    private String goodsSn;

    @ApiModelProperty("分类：1镜框 2近视镜片 3形眼镜 4护理液")
    private Byte category;

    @ApiModelProperty("品牌id")
    private String brandId;

    @ApiModelProperty("规格参数（度数、瞳距、材质等JSON）")
    private String specInfo;

    @ApiModelProperty("封面图片地址")
    private String coverImg;

    @ApiModelProperty("排序权重")
    private Integer sort;

    @ApiModelProperty("创建时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("门店专属售价")
    @TableField(exist = false)
    private BigDecimal shopSalePrice;

    @ApiModelProperty("门店单独库存")
    @TableField(exist = false)
    private Integer shopStock;

    @ApiModelProperty("门店内是否上架：0下架 1上架")
    @TableField(exist = false)
    @JsonSerialize(using = ByteToBooleanSerializer.class)
    @JsonDeserialize(using = BooleanToByteDeserializer.class)
    private Byte isShopShelf;

    @ApiModelProperty("品牌内容")
    @TableField(exist = false)
    private Brand brand;
}
