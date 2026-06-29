package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gxh.admin.common.jackson.BooleanToByteDeserializer;
import com.gxh.admin.common.jackson.ByteToBooleanSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 门店基础信息表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
@Data
@TableName("sys_shop")
@ApiModel(value = "Shop对象", description = "门店基础信息表")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("门店唯一ID")
    private String id;

    @ApiModelProperty("门店名称")
    private String name;

    @ApiModelProperty("门店固定营业电话")
    private String phone;

    @ApiModelProperty("省/自治区")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("县/区")
    private String district;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("经度（配送定位）")
    private BigDecimal longitude;

    @ApiModelProperty("纬度（配送定位）")
    private BigDecimal latitude;

    @ApiModelProperty("营业开始时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime businessStartTime;

    @ApiModelProperty("营业结束时间")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime businessEndTime;

    @ApiModelProperty("营业执照编号")
    private String businessLicense;

    @ApiModelProperty("门店LOGO图片地址")
    private String logoUrl;

    @ApiModelProperty("门店备注说明")
    private String remark;

    @ApiModelProperty("状态：1启用 0禁用")
    @JsonSerialize(using = ByteToBooleanSerializer.class)
    @JsonDeserialize(using = BooleanToByteDeserializer.class)
    private Byte status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人（平台管理员ID）")
    private String createBy;
}
