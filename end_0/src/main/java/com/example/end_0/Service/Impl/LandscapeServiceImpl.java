package com.example.end_0.Service.Impl;

import com.example.end_0.Mapper.LandscapeMapper;
import com.example.end_0.Mapper.t_lMapper;
import com.example.end_0.Pojo.entity.Landscape;
import com.example.end_0.Service.ImageService;
import com.example.end_0.Service.LandscapeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 景点服务实现类
 * 集成图片URL处理服务，支持多图片展示
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Service
public class LandscapeServiceImpl implements LandscapeService {

    @Autowired
    private LandscapeMapper landscapeMapper;

    @Autowired
    private t_lMapper tlMapper;

    @Autowired
    private ImageService imageService;

    @Override
    public void addLandscape(String name) {
        landscapeMapper.addLandscape(name);
    }

    @Override
    public void deleteLandscape(Integer landscape_id) {
        landscapeMapper.deleteLandscape(landscape_id);
    }

    @Override
    public void updateLandscape(Landscape landscape) {
        landscapeMapper.updateLandscape(landscape);
    }

    @Override
    public List<Map<String, Object>> getAllLandscapeWithUrls() {
        List<Landscape> landscapes = landscapeMapper.getAllLandscape();
        return landscapes.stream()
                .map(this::convertToMapWithUrls)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getLandscapeWithUrls(Integer landscape_id) {
        Landscape landscape = landscapeMapper.getLandscape(landscape_id);
        return landscape != null ? convertToMapWithUrls(landscape) : new HashMap<>();
    }

    @Override
    public Integer getIdByName(String name) {
        return landscapeMapper.getIdByName(name);
    }

    @Override
    public Double getScoreOfLandscape(Integer landscape_id) {
        return tlMapper.getScoreOfLandscape(landscape_id);
    }

    @Override
    public List<Landscape> getAllLandscape() {
        return landscapeMapper.getAllLandscape();
    }

    @Override
    public Landscape getLandscape(Integer landscape_id) {
        return landscapeMapper.getLandscape(landscape_id);
    }

    @Override
    public void updateLandscapeImages(Integer landscape_id, List<String> images) {
        landscapeMapper.updateLandscapeImages(landscape_id, images);
    }

    /**
     * 将景点对象转换为包含完整图片URL的Map
     */
    private Map<String, Object> convertToMapWithUrls(Landscape landscape) {
        Map<String, Object> result = new HashMap<>();
        result.put("landscape_id", landscape.getLandscape_id());
        result.put("name", landscape.getName());
        result.put("location", landscape.getLocation());
        result.put("telephone", landscape.getTelephone());
        result.put("description", landscape.getDescription());

        // 处理多图片URL
        if (landscape.getImages() != null && !landscape.getImages().isEmpty()) {
            List<String> imageUrls = imageService.generateLandscapeImageUrls(landscape.getImages());
            result.put("images", imageUrls);
            // 主图使用第一张图片
            result.put("pic_url", imageUrls.get(0));
        } else {
            result.put("images", new ArrayList<>());
            result.put("pic_url", "");
        }

        return result;
    }
}