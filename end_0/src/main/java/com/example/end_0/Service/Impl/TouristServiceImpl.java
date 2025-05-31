package com.example.end_0.Service.Impl;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Mapper.TouristMapper;
import com.example.end_0.Mapper.t_lMapper;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游客业务逻辑服务实现类
 * 实现TouristService接口，提供游客相关的具体业务逻辑
 * 包括游客信息管理和景点评分功能
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Service
public class TouristServiceImpl implements TouristService {

    /**
     * 游客数据访问层
     * 用于执行游客相关的数据库操作
     */
    @Autowired
    TouristMapper touristMapper;

    /**
     * 游客-景点关联数据访问层
     * 用于处理游客对景点的评分数据
     */
    @Autowired
    t_lMapper tlMapper;

    /**
     * 添加新游客
     * 将新游客信息保存到数据库
     *
     * @param tourist_name     游客用户名
     * @param tourist_password 游客密码
     */
    @Override
    public void addTourist(String tourist_name, String tourist_password) {
        touristMapper.addTourist(tourist_name, tourist_password);
    }

    /**
     * 删除游客
     * 从数据库中删除指定的游客记录
     *
     * @param tourist_id 要删除的游客ID
     */
    @Override
    public void deleteTourist(Integer tourist_id) {
        touristMapper.deleteTourist(tourist_id);
    }

    /**
     * 更新游客信息
     * 更新数据库中的游客信息
     *
     * @param tourist 包含更新信息的游客对象
     */
    @Override
    public void updateTourist(Tourist tourist) {
        touristMapper.updateTourist(tourist);
    }

    /**
     * 获取所有游客信息
     * 从数据库查询所有游客的信息列表
     *
     * @return 游客信息列表
     */
    @Override
    public List<Tourist> getAllTourist() {
        return touristMapper.getAllTourist();
    }

    /**
     * 根据ID获取游客信息
     * 从数据库查询指定ID的游客信息
     *
     * @param tourist_id 游客ID
     * @return 游客信息对象
     */
    @Override
    public Tourist getTourist(Integer tourist_id) {
        return touristMapper.getTourist(tourist_id);
    }

    /**
     * 根据用户名获取游客ID
     * 通过用户名查找对应的游客ID
     *
     * @param tourist_name 游客用户名
     * @return 对应的游客ID
     */
    @Override
    public Integer getIdByName(String tourist_name) {
        return touristMapper.getIdByName(tourist_name);
    }

    /**
     * 游客给景点评分
     * 处理游客对景点的评分逻辑，包括重复评分检查
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @param score        评分值
     * @return 操作结果，成功或失败信息
     */
    @Override
    public Result score(Integer tourist_id, Integer landscape_id, Integer score) {
        // 检查该游客是否已经为该景点评过分
        if (tlMapper.findScoring(tourist_id, landscape_id) != null)
            return Result.error("该用户已为该景点打过分");
        // 添加新的评分记录
        tlMapper.addScoring(tourist_id, landscape_id, score);
        return Result.success();
    }

    /**
     * 删除游客评分
     * 删除游客对指定景点的评分记录
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @return 操作结果，成功或失败信息
     */
    @Override
    public Result deleteScoring(Integer tourist_id, Integer landscape_id) {
        // 检查该游客是否为该景点评过分
        if (tlMapper.findScoring(tourist_id, landscape_id) == null)
            return Result.error("该用户未为该景点打过分");
        // 删除评分记录
        tlMapper.deleteScoring(tourist_id, landscape_id);
        return Result.success();
    }
}
