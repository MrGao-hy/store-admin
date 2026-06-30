package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
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
 * 眼镜品牌字典表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-30
 */
@Data
@TableName("sys_brand")
@ApiModel(value = "Brand对象", description = "眼镜品牌字典表")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("品牌ID")
    private String id;

    @ApiModelProperty("品牌名称")
    private String name;

    @ApiModelProperty("品牌logo地址")
    private String logo;

    @ApiModelProperty("品牌简介")
    private String description;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("状态：0禁用 1启用")
    @JsonSerialize(using = ByteToBooleanSerializer.class)
    @JsonDeserialize(using = BooleanToByteDeserializer.class)
    private Byte status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
