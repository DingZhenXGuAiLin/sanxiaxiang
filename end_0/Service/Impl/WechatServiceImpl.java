package com.example.end_0.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.dto.DecryptPhoneRequest;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.TouristService;
import com.example.end_0.Service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

/**
 * 微信服务实现类
 * 实现微信相关功能，包括手机号解密
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private TouristService touristService;

    @Override
    public Result decryptPhone(DecryptPhoneRequest request) {
        try {
            // 1. 参数验证
            if (request.getEncryptedData() == null || request.getIv() == null || request.getTouristId() == null) {
                return Result.error("参数不能为空");
            }

            // 2. 根据touristId获取用户的session_key
            Tourist tourist = touristService.getTourist(request.getTouristId());
            if (tourist == null) {
                return Result.error("用户不存在");
            }

            String sessionKey = tourist.getWx_session_key();
            if (sessionKey == null || sessionKey.trim().isEmpty()) {
                return Result.error("未找到用户会话信息，请重新登录");
            }

            // 3. 检查session_key是否过期
            if (tourist.getSession_key_expire_time() != null &&
                    LocalDateTime.now().isAfter(tourist.getSession_key_expire_time())) {
                return Result.error("会话已过期，请重新登录");
            }

            // 4. 开发环境特殊处理
            if (isDevMode(sessionKey)) {
                return handleDevModeDecryption(request, tourist);
            }

            // 5. 生产环境：正常解密数据
            String decryptedData = decryptData(request.getEncryptedData(), request.getIv(), sessionKey);

            // 6. 解析JSON
            JSONObject phoneJson = JSON.parseObject(decryptedData);

            // 7. 验证watermark
            JSONObject watermark = phoneJson.getJSONObject("watermark");
            if (watermark == null) {
                return Result.error("解密数据格式错误");
            }

            // 8. 更新用户手机号
            String phoneNumber = phoneJson.getString("phoneNumber");
            if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
                tourist.setPhone_number(phoneNumber);
                tourist.setUpdated_at(LocalDateTime.now());
                touristService.updateTourist(tourist);
            }

            return Result.success(phoneJson);

        } catch (Exception e) {
            return Result.error("解密失败：" + e.getMessage());
        }
    }

    /**
     * 判断是否为开发环境
     * 通过session_key是否以开发环境标识开头来判断
     */
    private boolean isDevMode(String sessionKey) {
        return sessionKey != null && sessionKey.startsWith("BgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCU=");
    }

    /**
     * 开发环境下的手机号解密处理
     * 直接返回虚拟的手机号数据，不进行真实解密
     */
    private Result handleDevModeDecryption(DecryptPhoneRequest request, Tourist tourist) {
        try {
            // 生成虚拟的手机号数据
            JSONObject phoneJson = new JSONObject();

            // 根据用户ID生成固定的虚拟手机号
            String phoneNumber = generateDevPhoneNumber(tourist.getTourist_id());
            String purePhoneNumber = phoneNumber.replaceAll("[^0-9]", "");

            phoneJson.put("phoneNumber", phoneNumber);
            phoneJson.put("purePhoneNumber", purePhoneNumber);
            phoneJson.put("countryCode", "+86");

            // 添加watermark（模拟微信格式）
            JSONObject watermark = new JSONObject();
            watermark.put("timestamp", System.currentTimeMillis() / 1000);
            watermark.put("appid", "wx1234567890"); // 开发环境的appid
            phoneJson.put("watermark", watermark);

            // 更新用户手机号
            tourist.setPhone_number(phoneNumber);
            tourist.setUpdated_at(LocalDateTime.now());
            touristService.updateTourist(tourist);

            // 开发环境日志
            System.out.println("=== 开发环境手机号解密 ===");
            System.out.println("用户ID: " + tourist.getTourist_id());
            System.out.println("虚拟手机号: " + phoneNumber);
            System.out.println("纯数字手机号: " + purePhoneNumber);
            System.out.println("==========================");

            return Result.success(phoneJson);

        } catch (Exception e) {
            return Result.error("开发环境解密失败：" + e.getMessage());
        }
    }

    /**
     * 生成开发环境的虚拟手机号
     */
    private String generateDevPhoneNumber(Integer touristId) {
        // 基于用户ID生成固定的手机号
        long baseNumber = 13800000000L;
        long phoneNumber = baseNumber + (touristId % 100000);
        return String.valueOf(phoneNumber);
    }

    @Override
    public String decryptData(String encryptedData, String iv, String sessionKey) {
        try {
            // Base64解码
            byte[] dataByte = Base64.getDecoder().decode(encryptedData);
            byte[] keyByte = Base64.getDecoder().decode(sessionKey);
            byte[] ivByte = Base64.getDecoder().decode(iv);

            // AES解密
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivByte);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            byte[] original = cipher.doFinal(dataByte);

            return new String(original, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("解密失败", e);
        }
    }
}