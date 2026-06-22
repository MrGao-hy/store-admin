package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户角色关联中间表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@TableName("sys_user_role")
@ApiModel(value = "UserRole对象", description = "用户角色关联中间表")
@Data
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键自增ID")
    private String id;

    @ApiModelProperty("用户ID字符串，关联sys_user.id")
    private String userId;

    @ApiModelProperty("角色ID字符串，关联sys_role.id")
    private String roleId;

    @ApiModelProperty("分配时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("权限id集合")
    @TableField(exist = false)
    private String[] roleIds;
}
