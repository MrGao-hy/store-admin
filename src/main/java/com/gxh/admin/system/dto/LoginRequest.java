package com.gxh.admin.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "登录请求", description = "用户登录请求参数")
public class LoginRequest {

    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "admin")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "验证码UUID", required = true)
    @NotBlank(message = "验证码UUID不能为空")
    private String uuid;

    @ApiModelProperty(value = "验证码", required = true, example = "Abc1")
    @NotBlank(message = "验证码不能为空")
    private String code;
}