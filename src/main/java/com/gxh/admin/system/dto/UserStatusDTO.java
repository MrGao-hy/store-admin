package com.gxh.admin.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("修改用户状态请求")
public class UserStatusDTO {
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("状态：1正常 0禁用")
    private Byte status;
}