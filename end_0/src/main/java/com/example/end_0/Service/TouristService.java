package com.example.end_0.Service;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.entity.Tourist;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游客业务逻辑服务接口
 * 定义游客相关的业务操作，包括基本的CRUD操作和评分功能
 * 在Controller和Mapper之间提供业务逻辑处理
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Service
public interface TouristService {

    /**
     * 添加新游客
     * 注册新的游客账户，需要验证用户名唯一性
     *
     * @param tourist_name     游客用户名
     * @param tourist_password 游客密码
     */
    void addTourist(String tourist_name, String tourist_password);

    /**
     * 删除游客
     * 根据游客ID删除游客账户及相关数据
     *
     * @param tourist_id 要删除的游客ID
     */
    void deleteTourist(Integer tourist_id);

    /**
     * 更新游客信息
     * 更新游客的个人信息，如用户名、密码、头像等
     *
     * @param tourist 包含更新信息的游客对象
     */
    void updateTourist(Tourist tourist);

    /**
     * 获取所有游客信息
     * 查询系统中所有注册游客的信息列表
     *
     * @return 游客信息列表
     */
    List<Tourist> getAllTourist();

    /**
     * 根据ID获取游客信息
     * 查询指定ID的游客详细信息
     *
     * @param tourist_id 游客ID
     * @return 游客信息对象
     */
    Tourist getTourist(Integer tourist_id);

    /**
     * 根据用户名获取游客ID
     * 通过用户名查找对应的游客ID，常用于登录验证
     *
     * @param tourist_name 游客用户名
     * @return 对应的游客ID
     */
    Integer getIdByName(String tourist_name);

    /**
     * 游客给景点评分
     * 游客对指定景点进行评分，评分范围通常为1-5分
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @param score        评分值
     * @return 操作结果，包含成功或失败信息
     */
    Result score(Integer tourist_id, Integer landscape_id, Integer score);

    /**
     * 删除游客评分
     * 删除游客对指定景点的评分记录
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @return 操作结果，包含成功或失败信息
     */
    Result deleteScoring(Integer tourist_id, Integer landscape_id);
}
