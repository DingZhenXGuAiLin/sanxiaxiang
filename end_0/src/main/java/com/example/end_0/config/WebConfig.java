package com.example.end_0.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 实现WebMvcConfigurer接口，用于自定义Spring MVC的配置
 * 主要用于注册拦截器、配置静态资源处理等
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 日志拦截器
     * 用于记录HTTP请求的详细信息
     */
    @Autowired
    private LogInterceptor logInterceptor;

    /**
     * 添加拦截器配置
     * 将自定义的拦截器注册到Spring MVC的拦截器链中
     * 拦截器会在请求处理前后执行相应的逻辑
     *
     * @param registry 拦截器注册器，用于注册和配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册日志拦截器，拦截所有请求
        registry.addInterceptor(logInterceptor);
    }
}
