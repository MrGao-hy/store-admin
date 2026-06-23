package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户-门店绑定表（店长、店员归属门店）
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
@TableName("sys_user_shop")
@ApiModel(value = "UserShop对象", description = "用户-门店绑定表（店长、店员归属门店）")
public class UserShop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("系统用户ID")
    private String userId;

    @ApiModelProperty("所属门店ID")
    private String shopId;

    @ApiModelProperty("绑定时间")
    private LocalDateTime bindTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public LocalDateTime getBindTime() {
        return bindTime;
    }

    public void setBindTime(LocalDateTime bindTime) {
        this.bindTime = bindTime;
    }

    @Override
    public String toString() {
        return "UserShop{" +
            "id = " + id +
            ", userId = " + userId +
            ", shopId = " + shopId +
            ", bindTime = " + bindTime +
        "}";
    }
}
