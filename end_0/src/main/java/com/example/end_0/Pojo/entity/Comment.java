package com.example.end_0.Pojo.entity;

import lombok.Data;

/**
 * 评论实体类
 * 用于存储游客对景点的评论信息
 * 对应数据库中的comment表
 * 建立了游客和景点之间的关联关系
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Data
public class Comment {

    /**
     * 景点ID（外键）
     * 关联landscape表的主键，标识评论所属的景点
     */
    Integer landscape_id;

    /**
     * 游客ID（外键）
     * 关联tourist表的主键，标识发表评论的游客
     */
    Integer tourist_id;

    /**
     * 评论内容
     * 游客对景点的具体评价和感受描述
     */
    String content;

    /**
     * 评论时间
     * 记录评论发表的具体时间，格式通常为字符串形式的时间戳
     */
    String time;
}
