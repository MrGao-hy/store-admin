package com.gxh.admin.system.service;

import com.gxh.admin.common.Result;
import com.gxh.admin.system.dto.LoginRequest;
import com.gxh.admin.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gaoxianhua
 * @since 2026-06-18
 */
public interface IUserService extends IService<User> {

    Result<String> registerService(User user);

    Result<User> loginService(LoginRequest loginRequest, HttpServletResponse response);

    Result<User> getUserInfo(String userId);

}
