package com.example.end_0.Service;

import com.example.end_0.Pojo.entity.Landscape;

import java.util.List;
import java.util.Map;

/**
 * 景点服务接口
 * 提供景点信息的业务逻辑处理，包括图片URL处理和多图片支持
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
public interface LandscapeService {

    /**
     * 添加新景点
     */
    void addLandscape(String name);

    /**
     * 删除景点
     */
    void deleteLandscape(Integer landscape_id);

    /**
     * 更新景点信息
     */
    void updateLandscape(Landscape landscape);

    /**
     * 获取所有景点（包含完整图片URL）
     */
    List<Map<String, Object>> getAllLandscapeWithUrls();

    /**
     * 根据ID获取景点（包含完整图片URL）
     */
    Map<String, Object> getLandscapeWithUrls(Integer landscape_id);

    /**
     * 根据名称获取景点ID
     */
    Integer getIdByName(String name);

    /**
     * 获取景点评分
     */
    Double getScoreOfLandscape(Integer landscape_id);

    /**
     * 获取原始景点数据（不处理URL）
     */
    List<Landscape> getAllLandscape();

    /**
     * 获取原始景点数据（不处理URL）
     */
    Landscape getLandscape(Integer landscape_id);

    /**
     * 更新景点图片列表
     */
    void updateLandscapeImages(Integer landscape_id, List<String> images);
}