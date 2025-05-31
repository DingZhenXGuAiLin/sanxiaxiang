package com.example.end_0.Pojo.entity;

import lombok.Data;

/**
 * 游客实体类
 * 用于存储游客的基本信息，包括用户名、密码、ID和头像
 * 对应数据库中的tourist表
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Data
public class Tourist {

    /**
     * 游客用户名
     * 用于游客登录和识别，应保证唯一性
     */
    String tourist_name;

    /**
     * 游客登录密码
     * 存储游客的登录凭证，实际应用中应进行加密处理
     */
    String tourist_password;

    /**
     * 游客唯一标识ID
     * 数据库主键，自动生成，用于唯一标识每个游客
     */
    Integer tourist_id;

    /**
     * 用户头像URL
     * 存储游客头像图片的访问路径或URL地址
     */
    String user_pic;
}
