package com.goodguy.blog.interceptor;

import com.goodguy.blog.util.JWTUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {

        // 放行 options 请求，否则无法让前端带上自定义的 header 信息，导致 sessionID 改变
        if (HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        String uri = httpServletRequest.getRequestURI();
        if(uri.indexOf("api/admin")>=0){
            try {
                Map user = JWTUtil.VerifyToken(httpServletRequest.getHeader("Authorization").replace("Bearer ", ""));
                if ((int) user.get("status") >= 2) {
                    return true;
                }
            }catch (Exception e){ }
            System.out.println("未授权访问");
            httpServletResponse.setStatus(401);
            return false;
        }

        return true;
    }
}
