package com.hqyj.erp_sys.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqyj.erp_sys.util.Auth;
import com.hqyj.erp_sys.util.JWTUtil;
import com.hqyj.erp_sys.util.ResultData;
import io.jsonwebtoken.Claims;
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
        //判断当前的请求是否属于本项目中的某个方法
        if (handler instanceof HandlerMethod) {
            //获取请求时携带的token
            String token = request.getHeader("token");
            //如果没有携带token，不允许继续访问，给前端返回一个结果
            if (token == null || "".equals(token)) {
                //构造前端所需ResultData对象对应的JSON字符串
                String resultJson = jsonTool.writeValueAsString(ResultData.error(-1, "请登录(token不存在)"));
                //将JSON字符串写到响应对象中
                response.getOutputStream().write(resultJson.getBytes("utf-8"));
                //设置响应类型为JSON
                response.setContentType("application/json;charset=utf-8");
                return false;
            }
            //如果携带token,解析token
            ResultData resultData = jwtUtil.validateToken(token);
            //如果验证失败，返回给前端对应的内容
            if (resultData.getCode() != 0) {
                //同样需要转换为JSON格式响应
                String resultJson = jsonTool.writeValueAsString(resultData);
                //将JSON字符串写到响应对象中
                response.getOutputStream().write(resultJson.getBytes("utf-8"));
                //设置响应类型为JSON
                response.setContentType("application/json;charset=utf-8");
                return false;
            }
            //验证权限
            //获取当前要访问的方法
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取当前要访问的方法
            //handlerMethod.getMethod();
            //获取当前要访问的方法的所在类
            Class<?> beanType = handlerMethod.getBeanType();
            //判断该类上是否有添加自定义注解
            boolean annotationPresent = beanType.isAnnotationPresent(Auth.class);
            if (annotationPresent) {
                //获取该类上的某个注解对象
                Auth auth = beanType.getAnnotation(Auth.class);
                //获取注解中的某个属性值
                String roles = auth.roles();
                //判断当前token中的信息是否满足要求
                //获取当前访问的token
                Claims claims = (Claims) resultData.getObj();
                String suRole = (String) claims.get("suRole");
                //判断自定义注解上的角色是否与当前请求的角色匹配
                if (!roles.contains(suRole)) {
                    //构造前端所需ResultData对象对应的JSON字符串
                    String resultJson = jsonTool.writeValueAsString(ResultData.error(5, "无权限"));
                    //将JSON字符串写道响应对象中
                    response.getOutputStream().write(resultJson.getBytes("utf-8"));
                    //设置响应类型为JSON
                    response.setContentType("application/json;charset=utf-8");
                    return false;
                }
            }
        }
        //如果通过验证，拦截器放行
        return true;
    }
}
