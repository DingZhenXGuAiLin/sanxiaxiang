package com.example.end_0.Mapper;

import com.example.end_0.Pojo.entity.AppResource;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 应用资源管理Mapper接口
 * 提供应用资源（图标、轮播图、封面等）的增删改查功能
 * 支持URL指纹机制和CDN缓存优化
 *
 * @author hyl
 * @version 1.0
 * @since 2025
 */
@Mapper
public interface AppResourceMapper {
        /**
         * 添加新的应用资源
         * 向数据库插入新的资源记录，自动设置创建时间
         *
         * @param appResource 资源对象
         */
        @Insert("INSERT INTO app_resources (resource_type, resource_key, resource_url, version_hash, " +
                        "sort_order, is_active, cache_duration, cdn_url, description, create_time, update_time) " +
                        "VALUES (#{resource_type}, #{resource_key}, #{resource_url}, #{version_hash}, " +
                        "#{sort_order}, #{is_active}, #{cache_duration}, #{cdn_url}, #{description}, " +
                        "NOW(), NOW())")
        @Options(useGeneratedKeys = true, keyProperty = "resource_id")
        void addResource(AppResource appResource);

        /**
         * 根据ID删除资源
         *
         * @param resourceId 资源ID
         */
        @Delete("DELETE FROM app_resources WHERE resource_id = #{resourceId}")
        void deleteResource(Integer resourceId);

        /**
         * 更新资源信息
         *
         * @param appResource 包含更新信息的资源对象
         */
        @Update("UPDATE app_resources SET resource_type = #{resource_type}, resource_key = #{resource_key}, " +
                        "resource_url = #{resource_url}, version_hash = #{version_hash}, sort_order = #{sort_order}, "
                        +
                        "is_active = #{is_active}, cache_duration = #{cache_duration}, cdn_url = #{cdn_url}, " +
                        "description = #{description}, update_time = NOW() WHERE resource_id = #{resource_id}")
        void updateResource(AppResource appResource);

        /**
         * 根据资源类型获取所有启用的资源
         *
         * @param resourceType 资源类型
         * @return 资源列表，按sort_order排序
         */
        @Select("SELECT * FROM app_resources WHERE resource_type = #{resourceType} AND is_active = 1 " +
                        "ORDER BY sort_order ASC")
        List<AppResource> getResourcesByType(String resourceType);

        /**
         * 根据资源类型和键获取特定资源
         *
         * @param resourceType 资源类型
         * @param resourceKey  资源键
         * @return 资源对象
         */
        @Select("SELECT * FROM app_resources WHERE resource_type = #{resourceType} AND resource_key = #{resourceKey} " +
                        "AND is_active = 1")
        AppResource getResourceByTypeAndKey(String resourceType, String resourceKey);

        /**
         * 获取所有启用的资源
         *
         * @return 所有启用的资源列表
         */
        @Select("SELECT * FROM app_resources WHERE is_active = 1 ORDER BY resource_type, sort_order")
        List<AppResource> getAllActiveResources();

        /**
         * 根据版本哈希查找资源
         * 用于检查资源是否需要更新指纹
         *
         * @param versionHash 版本哈希值
         * @return 资源列表
         */
        @Select("SELECT * FROM app_resources WHERE version_hash = #{versionHash}")
        List<AppResource> getResourcesByVersionHash(String versionHash);

        /**
         * 批量更新资源的版本哈希
         * 用于批量刷新资源指纹
         *
         * @param resourceType 资源类型
         * @param versionHash  新的版本哈希
         */
        @Update("UPDATE app_resources SET version_hash = #{versionHash}, update_time = NOW() " +
                        "WHERE resource_type = #{resourceType}")
        void batchUpdateVersionHash(String resourceType, String versionHash);

        /**
         * 统计各类型资源数量
         *
         * @return 资源统计信息
         */
        @Select("SELECT resource_type, COUNT(*) as count FROM app_resources WHERE is_active = 1 " +
                        "GROUP BY resource_type")
        List<java.util.Map<String, Object>> getResourceStatistics();
}
