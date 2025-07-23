package com.example.end_0.Pojo.dto;

import lombok.Data;

/**
 * 微信手机号解密请求DTO
 * 用于接收前端传递的解密参数
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Data
public class DecryptPhoneRequest {

    /**
     * 微信返回的加密数据
     */
    private String encryptedData;

    /**
     * 微信返回的初始向量
     */
    private String iv;

    /**
     * 用户ID（可选，用于直接更新用户信息）
     */
    private Integer touristId;
}