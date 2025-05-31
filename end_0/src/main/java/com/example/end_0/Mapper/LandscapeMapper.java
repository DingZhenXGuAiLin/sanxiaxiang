package com.example.end_0.Mapper;

import com.example.end_0.Pojo.entity.Landscape;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 景点数据访问层接口
 * 使用MyBatis注解方式定义SQL操作
 * 提供景点信息的增删改查功能，以及景点评分相关操作
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Mapper
public interface LandscapeMapper {

    /**
     * 添加新景点
     * 向数据库插入新的景点记录，只需要提供景点名称，其他信息后续可通过更新操作完善
     *
     * @param name 景点名称，应保证唯一性
     */
    @Insert("insert into landscape (name) values (#{name})")
    void addLandscape(String name);

    /**
     * 根据ID删除景点
     * 从数据库中删除指定ID的景点记录
     * 注意：删除景点会影响相关的评论和评分数据
     *
     * @param landscape_id 要删除的景点ID
     */
    @Delete("delete from landscape where landscape_id=#{landscape_id}")
    void deleteLandscape(Integer landscape_id);

    /**
     * 更新景点信息
     * 更新指定景点的完整信息，包括名称、图片、位置、电话和描述
     *
     * @param landscape 包含更新信息的景点对象，必须包含landscape_id
     */
    @Update("update landscape set name=#{name},pic_url=#{pic_url},location=#{location},telephone=#{telephone},description=#{description} "
            +
            "where landscape_id=#{landscape_id}")
    void updateLandscape(Landscape landscape);

    /**
     * 获取所有景点信息
     * 查询数据库中所有景点的完整信息
     *
     * @return 包含所有景点信息的列表
     */
    @Select("select * from landscape")
    List<Landscape> getAllLandscape();

    /**
     * 根据ID获取景点信息
     * 查询指定ID的景点详细信息
     *
     * @param landscape_id 景点ID
     * @return 对应的景点对象，如果不存在则返回null
     */
    @Select("select * from landscape where landscape_id=#{landscape_id}")
    Landscape getLandscape(Integer landscape_id);

    /**
     * 根据景点名称获取景点ID
     * 通过景点名称查找对应的景点ID，常用于验证景点名称唯一性
     *
     * @param name 景点名称
     * @return 对应的景点ID，如果景点名称不存在则返回null
     */
    @Select("select landscape_id from landscape where name=#{name}")
    Integer getIdByName(String name);

    /**
     * 获取景点的平均评分
     * 计算指定景点的平均评分，基于所有游客的评分数据
     *
     * @param landscape_id 景点ID
     * @return 景点的平均评分，如果没有评分则返回null
     */
    @Select("select avg(score) from t_l where landscape_id=#{landscape_id}")
    Double getScoreOfLandscape(Integer landscape_id);
}
