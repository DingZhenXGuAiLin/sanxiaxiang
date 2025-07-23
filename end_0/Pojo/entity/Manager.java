package com.example.end_0.Pojo.entity;

import lombok.Data;

/**
 * 管理员实体类
 * 用于存储系统管理员的基本信息，包括用户名、密码和ID
 * 对应数据库中的manager表
 * 管理员拥有系统管理权限，可以管理景点、游客等信息
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Data
public class Manager {

    /**
     * 管理员用户名
     * 用于管理员登录系统，应保证唯一性
     */
    String manager_name;

    /**
     * 管理员登录密码
     * 存储管理员的登录凭证，实际应用中应进行加密处理
     */
    String manager_password;

    /**
     * 管理员唯一标识ID
     * 数据库主键，自动生成，用于唯一标识每个管理员
     */
    Integer manager_id;
}
