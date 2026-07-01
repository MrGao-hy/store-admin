package com.gxh.admin.config;

import com.gxh.admin.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 放行OPTIONS请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = null;

        // 从Cookie中获取token
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":40001,\"message\":\"未登录，请先登录\"}");
            return false;
        }

        if (!JwtUtil.validateToken(token)) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":40001,\"message\":\"token已过期，请重新登录\"}");
            return false;
        }

        String userId = JwtUtil.getUserIdFromToken(token);
        request.setAttribute("userId", userId);

        List<String> roleCodes = JwtUtil.getRoleCodesFromToken(token);
        request.setAttribute("roleCodes", roleCodes);


        String shopId = JwtUtil.getShopIdFromToken(token);
        request.setAttribute("shopId", shopId);

        return true;
    }
}
