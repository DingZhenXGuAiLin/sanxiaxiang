package com.example.end_0.Mapper;

import com.example.end_0.Pojo.entity.Landscape;
import com.example.end_0.config.StringListTypeHandler;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 景点数据访问层接口
 * 使用MyBatis注解方式定义SQL操作
 * 提供景点信息的增删改查功能，支持多图片JSON数组存储
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
         * 更新景点信息（支持多图片）
         * 更新指定景点的完整信息，包括名称、多图片数组、位置、电话和描述
         *
         * @param landscape 包含更新信息的景点对象，必须包含landscape_id
         */
        @Update("update landscape set name=#{name}, images=#{images,typeHandler=com.example.end_0.config.StringListTypeHandler}, "
                        +
                        "location=#{location}, telephone=#{telephone}, description=#{description}, " +
                "latitude=#{latitude}, longitude=#{longitude}"+
                        "where landscape_id=#{landscape_id}")
        void updateLandscape(Landscape landscape);

        /**
         * 获取所有景点信息（支持多图片）
         * 查询数据库中所有景点的完整信息，包括多图片数组
         *
         * @return 包含所有景点信息的列表
         */
        @Select("select * from landscape")
        @Results({
                        @Result(column = "landscape_id", property = "landscape_id"),
                        @Result(column = "name", property = "name"),
                        @Result(column = "images", property = "images", typeHandler = StringListTypeHandler.class),
                        @Result(column = "location", property = "location"),
                        @Result(column = "telephone", property = "telephone"),
                        @Result(column = "description", property = "description"),
                        @Result(column = "longitude", property = "longitude"),
                        @Result(column = "latitude", property = "latitude"),
        })
        List<Landscape> getAllLandscape();

        /**
         * 根据ID获取景点信息（支持多图片）
         * 查询指定ID的景点详细信息，包括多图片数组
         *
         * @param landscape_id 景点ID
         * @return 对应的景点对象，如果不存在则返回null
         */
        @Select("select * from landscape where landscape_id=#{landscape_id}")
        @Results({
                        @Result(column = "landscape_id", property = "landscape_id"),
                        @Result(column = "name", property = "name"),
                        @Result(column = "images", property = "images", typeHandler = StringListTypeHandler.class),
                        @Result(column = "location", property = "location"),
                        @Result(column = "telephone", property = "telephone"),
                        @Result(column = "description", property = "description"),
                       @Result(column = "longitude", property = "longitude"),
                       @Result(column = "latitude", property = "latitude"),
        })



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
         * 更新景点的多图片数组
         * 专门用于更新景点的图片列表
         *
         * @param landscape_id 景点ID
         * @param images       图片路径列表
         */
        @Update("update landscape set images=#{images,typeHandler=com.example.end_0.config.StringListTypeHandler} " +
                        "where landscape_id=#{landscape_id}")
        void updateLandscapeImages(@Param("landscape_id") Integer landscape_id,
                        @Param("images") List<String> images);
}