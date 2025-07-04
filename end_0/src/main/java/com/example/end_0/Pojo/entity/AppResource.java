package com.example.end_0.Pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 通用应用资源实体类
 * 用于统一管理所有静态资源（图标、轮播图、封面等）
 * 对应数据库中的app_resources表
 * 支持URL指纹机制和CDN缓存优化
 *
 * @param resource_id    资源ID，数据库主键，自增
 * @param resource_type  资源类型，支持icon、banner、cover、tabbar等几乎静态的资源
 * @param resource_key   资源键，资源唯一标识，用于区分不同资源，如home-icon, banner-01
 * @param resource_url   资源URL，资源存储路径
 * @param version_hash  资源哈希,用于计算URL指纹,用于CDN缓存优化
 * @param sort_order     排序顺序,用于轮播图，数字越小，排序越靠前
 * @param is_active      是否激活,用于控制资源是否可见
 * @param cache_duration 缓存时长,用于控制资源缓存时间
 * @param cdn_url        CDN URL(可选)，用于CDN缓存优化
 * @param description    描述,用于描述资源信息，管理后台显示和资源管理
 * @param create_time    创建时间,用于记录资源创建时间
 * @param update_time    更新时间,用于记录资源更新时间
 *
 * @author hyl
 * @version 1.0
 * @since 2025
 */
@Data
public class AppResource {
    private Integer resource_id;
    private String resource_type;
    private String resource_key;
    private String resource_url;
    private String version_hash;
    private Integer sort_order;
    private Boolean is_active;
    private Integer cache_duration;
    private String cdn_url;
    private String description;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
