package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gxh.admin.common.jackson.BooleanToByteDeserializer;
import com.gxh.admin.common.jackson.ByteToBooleanSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
@Data
@TableName("sys_menu")
@ApiModel(value = "Menu对象", description = "系统菜单表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("父菜单ID（0表示顶级菜单）")
    private String parentId;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("类型 0目录 1菜单 2按钮")
    private Byte type;

    @ApiModelProperty("路由路径")
    private String path;

    @ApiModelProperty("前端组件路径")
    private String component;

    @ApiModelProperty("权限标识（如 sys:user:list）")
    private String permission;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("是否显示 1显示 0隐藏")
    @JsonSerialize(using = ByteToBooleanSerializer.class)
    @JsonDeserialize(using = BooleanToByteDeserializer.class)
    private Byte visible;

    @ApiModelProperty("是否缓存")
    @JsonSerialize(using = ByteToBooleanSerializer.class)
    @JsonDeserialize(using = BooleanToByteDeserializer.class)
    private Byte keepAlive;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("子菜单列表")
    @TableField(exist = false)
    private List<Menu> children;

}
