package com.example.end_0.Pojo.entity;

import lombok.Data;

/**
 * 景点实体类
 * 用于存储旅游景点的详细信息，包括名称、位置、联系方式等
 * 对应数据库中的landscape表
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Data
public class Landscape {

    /**
     * 景点名称
     * 景点的正式名称，用于展示和搜索
     */
    String name;

    /**
     * 景点唯一标识ID
     * 数据库主键，自动生成，用于唯一标识每个景点
     */
    Integer landscape_id;

    /**
     * 景点图片URL
     * 存储景点主要展示图片的访问路径或URL地址
     */
    String pic_url;

    /**
     * 景点地理位置
     * 景点的详细地址或地理位置描述
     */
    String location;

    /**
     * 景点联系电话
     * 游客咨询或预订时可使用的联系电话
     */
    String telephone;

    /**
     * 景点详细描述
     * 景点的详细介绍，包括特色、历史、游玩建议等信息
     */
    String description;
}
