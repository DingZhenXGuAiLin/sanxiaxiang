package com.example.end_0.Pojo.entity;

import lombok.Data;
import java.time.LocalDateTime;

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

    /**
     * 微信OpenID
     * 微信小程序用户的唯一标识，用于微信登录
     */
    String wx_openid;

    /**
     * 微信UnionID
     * 微信开放平台账号下的唯一标识，可能为空
     */
    String wx_unionid;

    /**
     * 微信Session Key
     * 微信小程序用户的会话密钥，用于解密用户数据
     */
    String wx_session_key;

    /**
     * Session Key过期时间
     * 微信Session Key的过期时间
     */
    LocalDateTime session_key_expire_time;

    /**
     * 手机号码
     * 用户的手机号码，可通过微信授权获取
     */
    String phone_number;

    /**
     * 登录方式
     * manual: 手动注册登录
     * wechat: 微信小程序登录
     */
    String login_type;

    /**
     * 创建时间
     * 用户注册时间
     */
    LocalDateTime created_at;

    /**
     * 更新时间
     * 用户信息最后更新时间
     */
    LocalDateTime updated_at;
}