package com.example.end_0;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 三峡乡旅游景点管理系统主启动类
 * Spring Boot应用程序的入口点
 *
 * @author hyl,zty,tcl
 * @version 1.0
 * @since 2024
 */
@Slf4j
@SpringBootApplication
public class End0Application {

    public static void main(String[] args) {
        // 加载.env文件中的环境变量
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMissing()
                    .load();

            // 将.env文件中的环境变量设置到系统属性中
            dotenv.entries().forEach(entry -> {
                System.setProperty(entry.getKey(), entry.getValue());
                log.info("加载环境变量: {} = {}", entry.getKey(), "***");
            });

            log.info("成功加载环境变量配置文件");
        } catch (Exception e) {
            log.error("加载环境变量配置文件失败: {}", e.getMessage());
            // 不终止程序，继续启动
        }

        // 启动Spring Boot应用程序
        SpringApplication.run(End0Application.class, args);
        log.info("服务器启动成功");
    }
}