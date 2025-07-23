package com.example.end_0.Mapper;

import com.example.end_0.Pojo.entity.Tourist;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 游客数据访问层接口
 * 使用MyBatis注解方式定义SQL操作
 * 提供游客信息的增删改查功能，支持微信登录
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Mapper
public interface TouristMapper {

        /**
         * 添加新游客（完整版本，支持微信登录）
         * 向数据库插入新的游客记录，包含微信相关信息
         * 使用@Options注解获取自动生成的主键
         *
         * @param tourist 游客对象，包含所有必要信息
         */
        @Insert("INSERT INTO tourist " +
                        "(tourist_name, tourist_password, user_pic, wx_openid, wx_unionid, wx_session_key, session_key_expire_time, phone_number, login_type, created_at, updated_at) "
                        +
                        "VALUES " +
                        "(#{tourist_name}, #{tourist_password}, #{user_pic}, #{wx_openid}, #{wx_unionid}, #{wx_session_key}, #{session_key_expire_time}, #{phone_number}, #{login_type}, NOW(), NOW())")
        @Options(useGeneratedKeys = true, keyProperty = "tourist_id")
        void addTourist(Tourist tourist);

        /**
         * 根据ID删除游客
         * 从数据库中删除指定ID的游客记录
         *
         * @param tourist_id 要删除的游客ID
         */
        @Delete("DELETE FROM tourist WHERE tourist_id=#{tourist_id}")
        void deleteTourist(Integer tourist_id);

        /**
         * 更新游客信息（动态SQL）
         * 只更新非null字段，支持部分字段更新
         *
         * @param tourist 包含更新信息的游客对象，必须包含tourist_id
         */
        @Update("<script>" +
                        "UPDATE tourist SET " +
                        "<if test='tourist_name != null'>tourist_name = #{tourist_name},</if>" +
                        "<if test='tourist_password != null'>tourist_password = #{tourist_password},</if>" +
                        "<if test='user_pic != null'>user_pic = #{user_pic},</if>" +
                        "<if test='wx_openid != null'>wx_openid = #{wx_openid},</if>" +
                        "<if test='wx_unionid != null'>wx_unionid = #{wx_unionid},</if>" +
                        "<if test='wx_session_key != null'>wx_session_key = #{wx_session_key},</if>" +
                        "<if test='session_key_expire_time != null'>session_key_expire_time = #{session_key_expire_time},</if>"
                        +
                        "<if test='phone_number != null'>phone_number = #{phone_number},</if>" +
                        "<if test='login_type != null'>login_type = #{login_type},</if>" +
                        "updated_at = NOW() " +
                        "WHERE tourist_id = #{tourist_id}" +
                        "</script>")
        void updateTourist(Tourist tourist);

        /**
         * 获取所有游客信息
         * 查询数据库中所有游客的完整信息
         *
         * @return 包含所有游客信息的列表
         */
        @Select("SELECT * FROM tourist")
        List<Tourist> getAllTourist();

        /**
         * 根据ID获取游客信息
         * 查询指定ID的游客详细信息
         *
         * @param tourist_id 游客ID
         * @return 对应的游客对象，如果不存在则返回null
         */
        @Select("SELECT * FROM tourist WHERE tourist_id=#{tourist_id}")
        Tourist getTourist(Integer tourist_id);

        /**
         * 根据用户名获取游客ID（保留用于手动注册时的用户名唯一性检查）
         * 注意：此方法仅用于手动注册时检查用户名是否已被使用
         *
         * @param tourist_name 游客用户名
         * @return 对应的游客ID，如果用户名不存在则返回null
         */
        @Select("SELECT tourist_id FROM tourist WHERE tourist_name=#{tourist_name}")
        Integer getIdByName(String tourist_name);

        /**
         * 根据微信OpenID获取游客信息
         * 通过微信OpenID查找对应的游客信息，用于微信登录
         *
         * @param wx_openid 微信OpenID
         * @return 对应的游客对象，如果不存在则返回null
         */
        @Select("SELECT * FROM tourist WHERE wx_openid=#{wx_openid}")
        Tourist getTouristByOpenId(String wx_openid);

        /**
         * 根据微信UnionID获取游客信息
         * 通过微信UnionID查找对应的游客信息
         *
         * @param wx_unionid 微信UnionID
         * @return 对应的游客对象，如果不存在则返回null
         */
        @Select("SELECT * FROM tourist WHERE wx_unionid=#{wx_unionid}")
        Tourist getTouristByUnionId(String wx_unionid);

        /**
         * 检查微信OpenID是否已存在
         * 用于验证微信用户是否已注册
         *
         * @param wx_openid 微信OpenID
         * @return 存在返回true，否则返回false
         */
        @Select("SELECT COUNT(*) > 0 FROM tourist WHERE wx_openid=#{wx_openid}")
        boolean existsByOpenId(String wx_openid);

        /**
         * 检查用户名是否已存在（仅用于手动注册）
         * 用于手动注册时验证用户名是否已被使用
         *
         * @param tourist_name 用户名
         * @return 存在返回true，否则返回false
         */
        @Select("SELECT COUNT(*) > 0 FROM tourist WHERE tourist_name=#{tourist_name} AND login_type='manual'")
        boolean existsByNameForManualRegistration(String tourist_name);

        /**
         * 更新用户的session_key
         * 用于保存微信登录时获取的session_key
         *
         * @param tourist_id 用户ID
         * @param sessionKey 会话密钥
         * @param expireTime 过期时间
         */
        @Update("UPDATE tourist SET wx_session_key = #{sessionKey}, session_key_expire_time = #{expireTime}, updated_at = NOW() WHERE tourist_id = #{tourist_id}")
        void updateSessionKey(@Param("tourist_id") Integer tourist_id, @Param("sessionKey") String sessionKey,
                        @Param("expireTime") java.time.LocalDateTime expireTime);
}