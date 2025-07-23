package com.example.end_0.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 图片资源配置类
 * 统一管理图片相关的配置信息，包括OSS、CDN、缓存等配置
 * 支持URL指纹机制和多级缓存策略
 *
 * @param baseUrl                       OSS存储桶基础URL，对象存储服务的基础访问地址
 * @param cdnUrl                        CDN加速域名，用于提供更快的图片访问速度
 * @param cdnEnabled                    是否启用CDN，true: 优先使用CDN域名，false: 直接使用OSS域名
 * @param defaultCacheDuration          默认缓存时长（秒），用于设置HTTP缓存头的默认值
 * @param staticCacheDuration           静态资源缓存时长（秒），用于图标、轮播图等长期不变的静态资源
 * @param avatarCacheDuration           用户头像缓存时长（秒），用于用户上传的头像等可能变化的资源
 * @param fingerprintEnabled            是否启用URL指纹，true: 启用后会在URL中添加版本标识用于缓存控制
 * @param fingerprintAlgorithm          指纹生成算法，支持：MD5、SHA256、TIMESTAMP、VERSION
 * @param fingerprintFormat             指纹URL格式，支持：QUERY、PATH、FILENAME
 * @param compressionEnabled            是否启用压缩传输，true: 启用Gzip压缩减少传输体积
 * @param imageQuality                  图片质量压缩等级，取值范围1-100，数值越高质量越好
 * @param mobileMaxWidth                移动端图片宽度限制，用于移动设备的响应式图片优化
 * @param webpEnabled                   是否启用WebP格式，true: 现代浏览器支持WebP可显著减少图片大小
 * @param allowedDomains                防盗链白名单域名，允许访问图片的域名列表，为空则不启用防盗链
 * @param watermarkEnabled              水印配置是否启用，true: 对重要图片添加水印保护
 * @param watermarkText                 水印文字内容，用于对重要图片添加水印保护
 * @param monitor                       监控告警配置，用于监控图片服务的性能和错误率
 * @param monitor.enabled               是否启用性能监控，true: 启用性能监控
 * @param monitor.responseTimeThreshold 响应时间告警阈值（毫秒），用于监控图片服务的响应时间
 * @param monitor.cacheHitRateThreshold 缓存命中率告警阈值（百分比），用于监控图片服务的缓存命中率
 * @param monitor.errorRateThreshold    错误率告警阈值（百分比），用于监控图片服务的错误率
 * @author hyl
 * @version 1.0
 * @since 2025
 */
@Data
@Component
@ConfigurationProperties(prefix = "image")
public class ImageConfig {

    /**
     * OSS存储桶基础URL
     * 对象存储服务的基础访问地址
     */
    private String baseUrl;

    /**
     * CDN加速域名
     * 用于提供更快的图片访问速度
     */
    private String cdnUrl;

    /**
     * 是否启用CDN
     * true: 优先使用CDN域名，false: 直接使用OSS域名
     */
    private Boolean cdnEnabled = true;

    /**
     * 默认缓存时长（秒）
     * 用于设置HTTP缓存头的默认值
     */
    private Integer defaultCacheDuration = 86400; // 24小时

    /**
     * 静态资源缓存时长（秒）
     * 用于图标、轮播图等长期不变的静态资源
     */
    private Integer staticCacheDuration = 31536000; // 1年

    /**
     * 用户头像缓存时长（秒）
     * 用于用户上传的头像等可能变化的资源
     */
    private Integer avatarCacheDuration = 3600; // 1小时

    /**
     * 是否启用URL指纹
     * 启用后会在URL中添加版本标识用于缓存控制
     */
    private Boolean fingerprintEnabled = true;

    /**
     * 指纹生成算法
     * 支持：MD5、SHA256、TIMESTAMP、VERSION
     */
    private String fingerprintAlgorithm = "MD5";

    /**
     * 指纹URL格式
     * QUERY: file.jpg?v=hash, PATH: v1/file.jpg, FILENAME: file-hash.jpg
     */
    private String fingerprintFormat = "QUERY";

    /**
     * 是否启用压缩传输
     * 启用Gzip压缩减少传输体积
     */
    private Boolean compressionEnabled = true;

    /**
     * 图片质量压缩等级
     * 取值范围1-100，数值越高质量越好
     */
    private Integer imageQuality = 85;

    /**
     * 移动端图片宽度限制
     * 用于移动设备的响应式图片优化
     */
    private Integer mobileMaxWidth = 750;

    /**
     * 是否启用WebP格式
     * 现代浏览器支持WebP可显著减少图片大小
     */
    private Boolean webpEnabled = true;

    /**
     * 防盗链白名单域名
     * 允许访问图片的域名列表，为空则不启用防盗链
     */
    private String[] allowedDomains;

    /**
     * 水印配置是否启用
     * 是否对重要图片添加水印保护
     */
    private Boolean watermarkEnabled = false;

    /**
     * 水印文字内容
     */
    private String watermarkText = "大湖镇文旅";

    /**
     * 监控告警配置
     */
    private MonitorConfig monitor = new MonitorConfig();

    /**
     * 监控告警配置内部类
     */
    @Data
    public static class MonitorConfig {
        /**
         * 是否启用性能监控
         */
        private Boolean enabled = true;

        /**
         * 响应时间告警阈值（毫秒）
         */
        private Integer responseTimeThreshold = 3000;

        /**
         * 缓存命中率告警阈值（百分比）
         */
        private Integer cacheHitRateThreshold = 80;

        /**
         * 错误率告警阈值（百分比）
         */
        private Integer errorRateThreshold = 5;
    }
}
