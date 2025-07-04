package com.example.end_0.Pojo.entity;

import lombok.Data;
import java.util.List;

/**
 * 景点实体类
 * 用于存储旅游景点的详细信息，包括名称、位置、联系方式等
 * 对应数据库中的landscape表
 *
 * @param name         景点名称，景点正式名称，用于展示和搜索
 * @param landscape_id 景点唯一标识ID，数据库主键，自动生成，用于唯一标识每个景点
 * @param images       景点图片URL，存储景点展示图片的URL地址
 * @param location     景点地理位置
 * @param telephone    景点联系电话
 * @param description  景点详细描述，景点的详细介绍，包括特色、历史、游玩建议等信息
 *
 * @author hyl
 * @version 1.0
 * @since 2025
 */
@Data
public class Landscape {

    private String name;

    private Integer landscape_id;

    private List<String> images;;

    private String location;

    private String telephone;

    private String description;
}
