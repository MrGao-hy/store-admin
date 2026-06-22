package com.gxh.admin.system.dto;
import lombok.Data;

import java.util.Date;

@Data
public class UserQueryDTO {

    /**
     * 页码
     */
    private Long current = 1L;

    /**
     * 每页数量
     */
    private Long size = 10L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
}
