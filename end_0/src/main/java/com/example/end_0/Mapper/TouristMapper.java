package com.example.end_0.Mapper;

import com.example.end_0.Pojo.entity.Tourist;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 游客数据访问层接口
 * 使用MyBatis注解方式定义SQL操作
 * 提供游客信息的增删改查功能
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Mapper
public interface TouristMapper {

        /**
         * 添加新游客
         * 向数据库插入新的游客记录，游客ID由数据库自动生成
         *
         * @param tourist_name     游客用户名，应保证唯一性
         * @param tourist_password 游客密码，建议在调用前进行加密
         */
        @Insert("insert into tourist (tourist_name,tourist_password) " +
                        "values (#{tourist_name},#{tourist_password})")
        void addTourist(String tourist_name, String tourist_password);

        /**
         * 根据ID删除游客
         * 从数据库中删除指定ID的游客记录
         * 注意：删除操作不可逆，建议在删除前进行确认
         *
         * @param tourist_id 要删除的游客ID
         */
        @Delete("delete from tourist where tourist_id=#{tourist_id}")
        void deleteTourist(Integer tourist_id);

        /**
         * 更新游客信息
         * 更新指定游客的用户名、密码和头像信息
         *
         * @param tourist 包含更新信息的游客对象，必须包含tourist_id
         */
        @Update("update tourist set tourist_name=#{tourist_name},tourist_password=#{tourist_password},user_pic=#{user_pic} "
                        +
                        "where tourist_id=#{tourist_id}")
        void updateTourist(Tourist tourist);

        /**
         * 获取所有游客信息
         * 查询数据库中所有游客的完整信息
         *
         * @return 包含所有游客信息的列表
         */
        @Select("select * from tourist")
        List<Tourist> getAllTourist();

        /**
         * 根据ID获取游客信息
         * 查询指定ID的游客详细信息
         *
         * @param tourist_id 游客ID
         * @return 对应的游客对象，如果不存在则返回null
         */
        @Select("select * from tourist where tourist_id=#{tourist_id}")
        Tourist getTourist(Integer tourist_id);

        /**
         * 根据用户名获取游客ID
         * 通过游客用户名查找对应的游客ID，常用于登录验证
         *
         * @param tourist_name 游客用户名
         * @return 对应的游客ID，如果用户名不存在则返回null
         */
        @Select("select tourist_id from tourist where tourist_name=#{tourist_name}")
        Integer getIdByName(String tourist_name);
}
