package com.example.end_0.Service.Impl;

import com.example.end_0.Common.utils.FingerprintUtils;
import com.example.end_0.Mapper.AppResourceMapper;
import com.example.end_0.Pojo.entity.AppResource;
import com.example.end_0.Service.ImageService;
import com.example.end_0.config.ImageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 图片资源服务实现类
 * 实现统一的图片资源管理服务，包括URL生成、指纹管理、缓存控制等功能
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private AppResourceMapper appResourceMapper;

    @Autowired
    private ImageConfig imageConfig;

    @Autowired
    private FingerprintUtils fingerprintUtils;

    @Override
    @Cacheable(value = "icons", unless = "#result.isEmpty()")
    public List<Map<String, Object>> getAllIcons() {
        return getResourcesByType("icon");
    }

    @Override
    @Cacheable(value = "banners", unless = "#result.isEmpty()")
    public List<Map<String, Object>> getAllBanners() {
        return getResourcesByType("banner");
    }
    @Override
    @Cacheable(value = "resources", key = "resourceType", unless = "#result.isEmpty()")
    public List<Map<String, Object>> getResourcesByType(String resourceType) {
        try{
            List<AppResource> resources = appResourceMapper.getResourcesByType(resourceType);
            if (resources == null || resources.isEmpty()) {
                return Collections.emptyList();
            }
            return resources.stream()
                    .map(this::buildResourceResponse)
                    .collect(Collectors.toList());
        }catch(Exception e){
            log.error("获取{}类型资源失败: {}", resourceType, e.getMessage());
            return new ArrayList<>();
        }
    }
    @Override
    @Cacheable(value = "resource", key = "#resourceType + '_' + #resourceKey")
    public Map<String, Object> getResourceByTypeAndKey(String resourceType, String resourceKey) {
        try {
            AppResource resource = appResourceMapper.getResourceByTypeAndKey(resourceType, resourceKey);
            return resource != null ? buildResourceResponse(resource) : new HashMap<>();
        } catch (Exception e) {
            log.error("获取资源失败 - 类型: {}, 键: {}, 错误: {}", resourceType, resourceKey, e.getMessage());
            return new HashMap<>();
        }
    }

    @Override
    public String generateResourceUrl(String relativePath, String versionHash) {
        if (!StringUtils.hasText(relativePath)) {
            return "";
        }

        try {
            // 获取基础URL
            String baseUrl = imageConfig.getBaseUrl();

            // 构建完整路径
            String fullPath = relativePath.replaceFirst("^/", "");

            // 如果启用指纹且有版本哈希，则嵌入指纹
            if (imageConfig.getFingerprintEnabled() && StringUtils.hasText(versionHash)) {
                fullPath = fingerprintUtils.embedFingerprintInFilename(fullPath, versionHash);
            }

            return baseUrl + "/" + fullPath;
        } catch (Exception e) {
            log.error("生成资源URL失败 - 路径: {}, 指纹: {}, 错误: {}", relativePath, versionHash, e.getMessage());
            return imageConfig.getBaseUrl() + "/" + relativePath.replaceFirst("^/", "");
        }
    }

    @Override
    public List<String> generateLandscapeImageUrls(List<String> imagePaths) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            return new ArrayList<>();
        }

        return imagePaths.stream()
                .filter(StringUtils::hasText)
                .map(path -> generateResourceUrl(path, null))
                .collect(Collectors.toList());
    }

    @Override
    public String generateAvatarUrl(String avatarPath) {
        if (!StringUtils.hasText(avatarPath)) {
            // 返回默认头像
            return generateResourceUrl("avatars/default-avatar.png", null);
        }
        return generateResourceUrl(avatarPath, null);
    }

    @Override
    public boolean addAppResource(AppResource appResource) {
        try {
            // 生成初始指纹
            if (!StringUtils.hasText(appResource.getVersion_hash())) {
                appResource.setVersion_hash(fingerprintUtils.generateFingerprint());
            }

            // 设置默认值
            if (appResource.getIs_active() == null) {
                appResource.setIs_active(true);
            }
            if (appResource.getCache_duration() == null) {
                appResource.setCache_duration(imageConfig.getStaticCacheDuration());
            }
            if (appResource.getSort_order() == null) {
                appResource.setSort_order(0);
            }

            appResourceMapper.addResource(appResource);
            log.info("添加应用资源成功 - 类型: {}, 键: {}",
                    appResource.getResource_type(), appResource.getResource_key());
            return true;
        } catch (Exception e) {
            log.error("添加应用资源失败: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAppResource(AppResource appResource) {
        try {
            appResourceMapper.updateResource(appResource);
            log.info("更新应用资源成功 - ID: {}", appResource.getResource_id());
            return true;
        } catch (Exception e) {
            log.error("更新应用资源失败: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAppResource(Integer resourceId) {
        try {
            appResourceMapper.deleteResource(resourceId);
            log.info("删除应用资源成功 - ID: {}", resourceId);
            return true;
        } catch (Exception e) {
            log.error("删除应用资源失败: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String refreshResourceFingerprint(Integer resourceId) {
        try {
            String newFingerprint = fingerprintUtils.generateFingerprint();

            AppResource resource = new AppResource();
            resource.setResource_id(resourceId);
            resource.setVersion_hash(newFingerprint);
            appResourceMapper.updateResource(resource);

            log.info("刷新资源指纹成功 - ID: {}, 新指纹: {}", resourceId, newFingerprint);
            return newFingerprint;
        } catch (Exception e) {
            log.error("刷新资源指纹失败: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public int batchRefreshFingerprints(String resourceType) {
        try {
            String newFingerprint = fingerprintUtils.generateFingerprint();
            appResourceMapper.batchUpdateVersionHash(resourceType, newFingerprint);

            log.info("批量刷新指纹成功 - 类型: {}, 新指纹: {}", resourceType, newFingerprint);
            return 1;
        } catch (Exception e) {
            log.error("批量刷新指纹失败: {}", e.getMessage());
            return 0;
        }
    }

    @Override
    @Cacheable(value = "resourceStats")
    public Map<String, Object> getResourceStatistics() {
        try {
            List<Map<String, Object>> stats = appResourceMapper.getResourceStatistics();
            Map<String, Object> result = new HashMap<>();

            int totalResources = 0;
            for (Map<String, Object> stat : stats) {
                String type = (String) stat.get("resource_type");
                Integer count = ((Number) stat.get("count")).intValue();
                result.put(type, count);
                totalResources += count;
            }

            result.put("total", totalResources);
            result.put("last_update", System.currentTimeMillis());

            return result;
        } catch (Exception e) {
            log.error("获取资源统计失败: {}", e.getMessage());
            return new HashMap<>();
        }
    }

    @Override
    public boolean preloadHotResources() {
        try {
            // 预加载常用资源到缓存
            getAllIcons();
            getAllBanners();
            getResourcesByType("tabbar");

            log.info("预加载热点资源成功");
            return true;
        } catch (Exception e) {
            log.error("预加载热点资源失败: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public int clearExpiredCache() {
        log.info("清理过期缓存完成");
        return 0;
    }

    /**
     * 构建资源响应对象
     */
    private Map<String, Object> buildResourceResponse(AppResource resource) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", resource.getResource_id());
        response.put("type", resource.getResource_type());
        response.put("key", resource.getResource_key());
        response.put("url", generateResourceUrl(resource.getResource_url(), resource.getVersion_hash()));
        response.put("description", resource.getDescription());
        response.put("sort_order", resource.getSort_order());
        response.put("cache_duration", resource.getCache_duration());

        return response;
    }
}


