package com.example.end_0.Common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * URL指纹生成工具类
 * 使用简单的时间戳算法生成指纹，支持文件名嵌入格式
 *
 * @author hyl
 * @version 1.0
 * @since 2025
 */

@Slf4j
@Component
public class FingerprintUtils {
    /**
     * 生成基于时间戳的URL指纹,取后8位
     *
     * @return 指纹URL
     */
    public static String generateFingerprint() {
        long timestamp = System.currentTimeMillis();
        return String.valueOf(timestamp).substring(8);
    }

    /**
     * 在文件名中嵌入指纹,
     * 通过提取文件名和扩展名，在两者中间插入指纹
     * 如果文件名中没有扩展名，则直接在文件名后面插入指纹
     *
     * @param originalUrl 原始文件名
     * @param fingerprint 指纹
     * @param extension   是包括.的扩展名
     * @return 指纹URL
     */
    public static String embedFingerprintInFilename(String originalUrl, String fingerprint) {
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            return originalUrl;
        }
        try {
            int lastDotIndex = originalUrl.lastIndexOf(".");
            if (lastDotIndex > 0) {
                // 有扩展名
                String fileName = originalUrl.substring(0, lastDotIndex);
                String extension = originalUrl.substring(lastDotIndex);
                return fileName + "-" + fingerprint + extension;
            } else {
                // 没有扩展名
                return originalUrl + "-" + fingerprint;
            }
        } catch (Exception e) {
            log.error("嵌入指纹失败", e.getMessage());
            return originalUrl;
        }
    }

    /**
     * 从带指纹的文件名中提取原始文件名
     * 用于处理反向操作
     *
     * @param fingerprintUrl 带指纹的URL
     * @return 原始URL
     */
    public String extractOriginalUrl(String fingerprintUrl) {
        if (fingerprintUrl == null || fingerprintUrl.trim().isEmpty()) {
            return fingerprintUrl;
        }
        try {
            int lastDashIndex = fingerprintUrl.lastIndexOf("-");
            int lastDotIndex = fingerprintUrl.lastIndexOf(".");
            if (lastDashIndex > 0) {
                // 有指纹
                String fileName = fingerprintUrl.substring(0, lastDashIndex);
                String extension = fingerprintUrl.substring(lastDotIndex);
                return fileName + extension;
            } else {
                // 没有指纹
                return fingerprintUrl;
            }
        } catch (Exception e) {
            log.error("提取原始URL失败", e.getMessage());
            return fingerprintUrl;
        }
    }
}