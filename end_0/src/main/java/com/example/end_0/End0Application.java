package com.example.end_0;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class End0Application {

    public static void main(String[] args) {
        SpringApplication.run(End0Application.class, args);
        log.info("服务器启动成功");
    }

}
