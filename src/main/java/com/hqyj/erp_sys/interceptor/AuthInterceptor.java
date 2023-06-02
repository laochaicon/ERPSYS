package com.hqyj.erp_sys.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.erp_sys.util.JWTUtil;
import com.hqyj.erp_sys.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtil jwtUtil;

    ObjectMapper jsonTool = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            String token = request.getHeader("token");
            //if (token == null || "".equals(token)) {
            if (token == null || "".equals(token)) {
                String resultJson = jsonTool.writeValueAsString(ResultData.error(-1, "请登录（token不存在）"));
                response.getOutputStream().write(resultJson.getBytes("utf-8"));
                response.setContentType("application/json;charset=utf-8");
                return false;
            }
            ResultData resultData = jwtUtil.validateToken(token);
            if (resultData.getCode() != 0) {
                String resultJson = jsonTool.writeValueAsString(resultData);
                response.getOutputStream().write(resultJson.getBytes("utf-8"));
                response.setContentType("application/json;charset=utf-8");
                return false;
            }
        }
        //如果通过验证，拦截器放行
        return true;
    }
}
