package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户-门店绑定表（店长、店员归属门店）
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-23
 */
@Data
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
