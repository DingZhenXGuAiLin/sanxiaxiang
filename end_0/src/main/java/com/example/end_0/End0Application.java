package com.example.end_0;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 三峡乡旅游景点管理系统主启动类
 * Spring Boot应用程序的入口点
 *
 * 功能说明：
 * - 启动Spring Boot应用程序
 * - 自动配置Spring容器
 * - 扫描并注册所有组件（Controller、Service、Mapper等）
 * - 启动内嵌的Web服务器（默认Tomcat）
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Slf4j
@SpringBootApplication
public class End0Application {

    /**
     * 应用程序主入口方法
     * 启动Spring Boot应用程序并初始化所有必要的组件
     *
     * @param args 命令行参数，可用于传递配置参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        SpringApplication.run(End0Application.class, args);
        // 记录启动成功日志
        log.info("服务器启动成功");
    }

}
