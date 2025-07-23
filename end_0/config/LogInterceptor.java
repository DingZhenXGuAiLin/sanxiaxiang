package com.example.end_0.config;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日志拦截器
 * 用于记录HTTP请求的详细信息
 * 实现HandlerInterceptor接口，用于拦截HTTP请求
 * 在请求处理前后执行相应的逻辑
 * @function preHandle 在请求处理之前执行，返回true表示继续执行，返回false表示拦截
 * @function postHandle 在请求处理之后执行,即在controller处理请求之后
 * @function afterCompletion 在请求处理完成后执行,即在视图渲染之后
 */

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception{
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        // 记录日志
        System.out.println("[" + request.getMethod() + "]" + request.getRequestURI());
        System.out.println("Request time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("executed in " + executeTime + "ms");
        System.out.println("Requester IP: " + request.getRemoteAddr());
        System.out.println("Response Status: " + response.getStatus());
        System.out.println("---------------------------------------------------------------------------");
    }
}
