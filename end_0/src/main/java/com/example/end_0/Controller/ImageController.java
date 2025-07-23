package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.ImageService;
import com.example.end_0.Service.TouristService;
import com.example.end_0.config.CosConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 图片资源控制器
 * 提供统一的图片资源访问接口，支持URL指纹机制和缓存优化
 * 前端通过这些接口获取所有图片资源，完全隐藏存储桶地址
 *
 * @author hyl
 * @version 1.0
 * @since 2025
 */
@Slf4j
@RestController
@RequestMapping("/api/images")
@CrossOrigin
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CosConfig cosConfig;

    @Autowired
    private TouristService touristService;

    /**
     * 获取所有图标资源
     * 返回应用中使用的所有图标的完整访问URL
     *
     * @return 图标资源列表
     */
    @GetMapping("/icons")
    public ResponseEntity<Result<List<Map<String, Object>>>> getAllIcons() {
        try {
            List<Map<String, Object>> icons = imageService.getAllIcons();

            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS))
                    .body(Result.success(icons));
        } catch (Exception e) {
            log.error("获取图标资源失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("获取图标资源失败"));
        }
    }

    /**
     * 获取所有轮播图资源
     * 返回首页轮播图的完整访问URL，按排序字段排序
     *
     * @return 轮播图资源列表
     */
    @GetMapping("/banners")
    public ResponseEntity<Result<List<Map<String, Object>>>> getAllBanners() {
        try {
            List<Map<String, Object>> banners = imageService.getAllBanners();

            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                    .body(Result.success(banners));
        } catch (Exception e) {
            log.error("获取轮播图资源失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("获取轮播图资源失败"));
        }
    }

    /**
     * 根据类型获取资源
     * 支持的类型：icon、banner、cover、tabbar等
     *
     * @param type 资源类型
     * @return 指定类型的资源列表
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<Result<List<Map<String, Object>>>> getResourcesByType(@PathVariable String type) {
        try {
            List<Map<String, Object>> resources = imageService.getResourcesByType(type);

            // 根据资源类型设置不同的缓存时间
            CacheControl cacheControl = getCacheControlByType(type);

            return ResponseEntity.ok()
                    .cacheControl(cacheControl)
                    .body(Result.success(resources));
        } catch (Exception e) {
            log.error("获取{}类型资源失败: {}", type, e.getMessage());
            return ResponseEntity.ok(Result.error("获取资源失败"));
        }
    }

    /**
     * 获取特定资源
     * 根据资源类型和键获取特定的资源信息
     *
     * @param type 资源类型
     * @param key  资源键
     * @return 特定资源信息
     */
    @GetMapping("/resource/{type}/{key}")
    public ResponseEntity<Result<Map<String, Object>>> getResourceByTypeAndKey(
            @PathVariable String type, @PathVariable String key) {
        try {
            Map<String, Object> resource = imageService.getResourceByTypeAndKey(type, key);

            if (resource.isEmpty()) {
                return ResponseEntity.ok(Result.error("资源不存在"));
            }

            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(2, TimeUnit.HOURS))
                    .body(Result.success(resource));
        } catch (Exception e) {
            log.error("获取资源失败 - 类型: {}, 键: {}, 错误: {}", type, key, e.getMessage());
            return ResponseEntity.ok(Result.error("获取资源失败"));
        }
    }

    /**
     * 生成景点图片URL列表
     * 用于景点详情页面显示多张图片
     *
     * @param request 包含图片路径列表的请求体
     * @return 完整URL列表
     */
    @PostMapping("/landscape/urls")
    public ResponseEntity<Result<List<String>>> generateLandscapeImageUrls(
            @RequestBody Map<String, List<String>> request) {
        try {
            List<String> imagePaths = request.get("imagePaths");
            List<String> imageUrls = imageService.generateLandscapeImageUrls(imagePaths);
            return ResponseEntity.ok(Result.success(imageUrls));
        } catch (Exception e) {
            log.error("生成景点图片URL失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("生成图片URL失败"));
        }
    }

    /**
     * 生成用户头像URL
     * 用于用户信息显示
     *
     * @param request 包含头像路径的请求体
     * @return 完整头像URL
     */
    @PostMapping("/avatar/url")
    public ResponseEntity<Result<String>> generateAvatarUrl(
            @RequestBody Map<String, String> request) {
        try {
            String avatarPath = request.get("avatarPath");
            String avatarUrl = imageService.generateAvatarUrl(avatarPath);
            return ResponseEntity.ok(Result.success(avatarUrl));
        } catch (Exception e) {
            log.error("生成头像URL失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("生成头像URL失败"));
        }
    }

    /**
     * 用户头像上传接口
     * 支持jpg、png、jpeg格式，最大10MB
     *
     * @param file      上传的头像文件
     * @param touristId 游客ID
     * @return 上传结果，包含头像的完整URL
     */
    @PostMapping("/avatar/upload")
    public ResponseEntity<Result<String>> uploadAvatar(
            @RequestParam("file") MultipartFile file,
            @RequestParam("touristId") Integer touristId) {
        try {
            // 验证文件
            if (file.isEmpty()) {
                return ResponseEntity.ok(Result.error("请选择要上传的文件"));
            }

            // 验证文件类型
            String contentType = file.getContentType();
            if (!isValidImageType(contentType)) {
                return ResponseEntity.ok(Result.error("只支持jpg、png、jpeg格式的图片"));
            }

            // 验证文件大小（10MB）
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.ok(Result.error("文件大小不能超过10MB"));
            }

            // 上传文件到COS
            String avatarPath = uploadToCloud(file, touristId);

            // 生成完整URL
            String avatarUrl = imageService.generateAvatarUrl(avatarPath);

            // 更新数据库中的用户头像字段
            updateTouristAvatar(touristId, avatarPath);

            return ResponseEntity.ok(Result.success(avatarUrl));
        } catch (Exception e) {
            log.error("头像上传失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("头像上传失败"));
        }
    }

    /**
     * 刷新资源指纹
     * 用于更新资源的缓存版本
     *
     * @param resourceId 资源ID
     * @return 新的指纹值
     */
    @PostMapping("/refresh/{resourceId}")
    public ResponseEntity<Result<String>> refreshResourceFingerprint(@PathVariable Integer resourceId) {
        try {
            String newFingerprint = imageService.refreshResourceFingerprint(resourceId);
            if (newFingerprint != null) {
                return ResponseEntity.ok(Result.success(newFingerprint));
            } else {
                return ResponseEntity.ok(Result.error("刷新指纹失败"));
            }
        } catch (Exception e) {
            log.error("刷新资源指纹失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("刷新指纹失败"));
        }
    }

    /**
     * 批量刷新指定类型的资源指纹
     *
     * @param type 资源类型
     * @return 刷新结果
     */
    @PostMapping("/refresh/type/{type}")
    public ResponseEntity<Result<Integer>> batchRefreshFingerprints(@PathVariable String type) {
        try {
            int count = imageService.batchRefreshFingerprints(type);
            return ResponseEntity.ok(Result.success(count));
        } catch (Exception e) {
            log.error("批量刷新指纹失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("批量刷新指纹失败"));
        }
    }

    /**
     * 获取资源统计信息
     *
     * @return 资源统计数据
     */
    @GetMapping("/statistics")
    public ResponseEntity<Result<Map<String, Object>>> getResourceStatistics() {
        try {
            Map<String, Object> stats = imageService.getResourceStatistics();
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
                    .body(Result.success(stats));
        } catch (Exception e) {
            log.error("获取资源统计失败: {}", e.getMessage());
            return ResponseEntity.ok(Result.error("获取统计信息失败"));
        }
    }

    /**
     * 验证图片文件类型
     */
    private boolean isValidImageType(String contentType) {
        return contentType != null && (contentType.equals("image/jpeg") ||
                contentType.equals("image/jpg") ||
                contentType.equals("image/png"));
    }

    /**
     * 上传文件到腾讯云COS
     */
    private String uploadToCloud(MultipartFile file, Integer touristId) throws Exception {
        // 生成文件名：avatars/tourist_123_timestamp.jpg
        String filename = "tourist_" + touristId + "_" + System.currentTimeMillis() +
                getFileExtension(file.getOriginalFilename());
        String avatarPath = "avatars/" + filename;

        // 初始化COS客户端
        COSCredentials cred = new BasicCOSCredentials(cosConfig.getSecretId(), cosConfig.getSecretKey());
        Region region = new Region(cosConfig.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            // 上传文件
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    cosConfig.getBucketName(),
                    avatarPath,
                    file.getInputStream(),
                    metadata);

            cosClient.putObject(putObjectRequest);
            log.info("头像上传成功: {}", avatarPath);

            return avatarPath;
        } finally {
            cosClient.shutdown();
        }
    }

    /**
     * 更新用户头像
     */
    private void updateTouristAvatar(Integer touristId, String avatarPath) {
        // 创建Tourist对象，只设置需要更新的字段
        Tourist tourist = new Tourist();
        tourist.setTourist_id(touristId);
        tourist.setUser_pic(avatarPath);

        // 使用现有的updateTourist方法（动态SQL只更新非null字段）
        touristService.updateTourist(tourist);
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.'));
    }

    /**
     * 根据资源类型设置缓存控制
     */
    private CacheControl getCacheControlByType(String type) {
        switch (type.toLowerCase()) {
            case "icon":
            case "tabbar":
                return CacheControl.maxAge(24, TimeUnit.HOURS); // 图标缓存24小时
            case "banner":
                return CacheControl.maxAge(30, TimeUnit.MINUTES); // 轮播图缓存30分钟
            case "avatar":
                return CacheControl.maxAge(1, TimeUnit.HOURS); // 头像缓存1小时
            default:
                return CacheControl.maxAge(2, TimeUnit.HOURS); // 默认缓存2小时
        }
    }
}