package com.example.end_0.Service;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.dto.DecryptPhoneRequest;
import org.springframework.stereotype.Service;

/**
 * 微信服务接口
 * 提供微信相关功能，包括手机号解密
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Service
public interface WechatService {

    /**
     * 解密微信手机号
     * 根据加密数据和用户ID解密获取真实手机号
     *
     * @param request 解密请求参数
     * @return 解密结果，包含手机号信息
     */
    Result decryptPhone(DecryptPhoneRequest request);

    /**
     * 解密数据
     * 使用AES算法解密微信加密数据
     *
     * @param encryptedData 加密数据
     * @param iv            初始向量
     * @param sessionKey    会话密钥
     * @return 解密后的JSON字符串
     */
    String decryptData(String encryptedData, String iv, String sessionKey);
}