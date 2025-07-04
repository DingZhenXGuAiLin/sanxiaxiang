package com.example.end_0.Service;

import com.example.end_0.Pojo.entity.AppResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 图片资源服务接口
 * 提供图片资源的管理和访问服务
 *
 * @author hyl
 * @version 1.0
 * @since 2025
 */
@Service
public interface ImageService {
    /**
     * 获取所有启用的图标资源
     *
     * @return 图标资源列表，包含完整的访问URL
     */
    List<Map<String, Object>> getAllIcons();

    /**
     * 获取所有启用的轮播图资源
     *
     * @return 轮播图资源列表，按排序字段排序
     */
    List<Map<String, Object>> getAllBanners();

    /**
     * 根据类型获取资源列表
     *
     * @param resourceType 资源类型
     * @return 资源列表，包含完整访问URL
     */
    List<Map<String, Object>> getResourcesByType(String resourceType);

    /**
     * 根据类型和键获取特定资源
     *
     * @param resourceType 资源类型
     * @param resourceKey 资源键
     * @return 资源信息，包含完整访问URL
     */
    Map<String, Object> getResourceByTypeAndKey(String resourceType, String resourceKey);

    /**
     * 生成资源的完整访问URL
     * 包含CDN优化、指纹机制和缓存控制
     *
     * @param relativePath 资源相对路径
     * @param versionHash 版本哈希值
     * @return 完整的访问URL
     */
    String generateResourceUrl(String relativePath, String versionHash);

    /**
     * 生成景点图片的完整URL列表
     *
     * @param imagePaths 图片相对路径列表
     * @return 完整URL列表
     */
    List<String> generateLandscapeImageUrls(List<String> imagePaths);

    /**
     * 生成用户头像的完整URL
     *
     * @param avatarPath 头像相对路径
     * @return 完整的头像URL
     */
    String generateAvatarUrl(String avatarPath);

    /**
     * 添加新的应用资源
     *
     * @param appResource 资源对象
     * @return 添加结果
     */
    boolean addAppResource(AppResource appResource);

    /**
     * 更新应用资源
     *
     * @param appResource 资源对象
     * @return 更新结果
     */
    boolean updateAppResource(AppResource appResource);

    /**
     * 删除应用资源
     *
     * @param resourceId 资源ID
     * @return 删除结果
     */
    boolean deleteAppResource(Integer resourceId);

    /**
     * 刷新资源指纹
     * 重新计算指定资源的版本哈希值
     *
     * @param resourceId 资源ID
     * @return 新的指纹值
     */
    String refreshResourceFingerprint(Integer resourceId);

    /**
     * 批量刷新指定类型的所有资源指纹
     *
     * @param resourceType 资源类型
     * @return 刷新的资源数量
     */
    int batchRefreshFingerprints(String resourceType);

    /**
     * 获取资源统计信息
     *
     * @return 各类型资源的统计数据
     */
    Map<String, Object> getResourceStatistics();

    /**
     * 预加载热点资源
     * 将常用资源预加载到缓存中
     *
     * @return 预加载结果
     */
    boolean preloadHotResources();

    /**
     * 清理过期缓存
     *
     * @return 清理的缓存数量
     */
    int clearExpiredCache();
}
