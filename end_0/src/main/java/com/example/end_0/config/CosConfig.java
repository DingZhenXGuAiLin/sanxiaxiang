package com.example.end_0.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯云COS配置类
 * 用于管理COS相关的配置信息
 *
 * @author hyl
 * @version 1.0
 * @since 2025
 */
@Data
@Component
@ConfigurationProperties(prefix = "cos")
public class CosConfig {
    /**
     * 腾讯云SecretId
     */
    private String secretId;

    /**
     * 腾讯云SecretKey
     */
    private String secretKey;

    /**
     * COS区域
     */
    private String region;

    /**
     * 存储桶名称
     */
    private String bucketName;
}