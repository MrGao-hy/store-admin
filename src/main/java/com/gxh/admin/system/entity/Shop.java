package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 门店基础信息表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
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
    private LocalTime businessStartTime;

    @ApiModelProperty("营业结束时间")
    private LocalTime businessEndTime;

    @ApiModelProperty("营业执照编号")
    private String businessLicense;

    @ApiModelProperty("门店LOGO图片地址")
    private String logoUrl;

    @ApiModelProperty("门店备注说明")
    private String remark;

    @ApiModelProperty("状态：1启用 0禁用 -1停业")
    private Byte status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人（平台管理员ID）")
    private String createBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public LocalTime getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(LocalTime businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public LocalTime getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(LocalTime businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "Shop{" +
            "id = " + id +
            ", name = " + name +
            ", phone = " + phone +
            ", province = " + province +
            ", city = " + city +
            ", district = " + district +
            ", address = " + address +
            ", longitude = " + longitude +
            ", latitude = " + latitude +
            ", businessStartTime = " + businessStartTime +
            ", businessEndTime = " + businessEndTime +
            ", businessLicense = " + businessLicense +
            ", logoUrl = " + logoUrl +
            ", remark = " + remark +
            ", status = " + status +
            ", createTime = " + createTime +
            ", updateTime = " + updateTime +
            ", createBy = " + createBy +
        "}";
    }
}
