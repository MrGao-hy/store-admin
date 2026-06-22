package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@TableName("sys_user")
@ApiModel(value = "User对象", description = "用户表")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("真实姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty("密码盐值")
    @JsonIgnore
    private String salt;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别(1男 0女)")
    private Byte sex;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区/县")
    private String district;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("状态(1正常 0禁用)")
    private Byte status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("角色信息")
    @TableField(exist = false)
    private List<Role> roles;
}
