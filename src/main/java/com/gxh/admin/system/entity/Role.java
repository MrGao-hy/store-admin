package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@TableName("sys_role")
@ApiModel(value = "Role对象", description = "角色表")
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
