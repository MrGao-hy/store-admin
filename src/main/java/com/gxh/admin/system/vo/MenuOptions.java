package com.gxh.admin.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MenuOptions {

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("菜单名称")
    private String name;

    private String parentId;

    private String sort;

    private List<MenuOptions> children;
}
