package com.gxh.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("父菜单ID（0表示顶级菜单）")
    private Long parentId;

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
    private Byte visible;

    @ApiModelProperty("是否缓存")
    private Byte keepAlive;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
