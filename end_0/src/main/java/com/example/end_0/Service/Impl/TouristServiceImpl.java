package com.example.end_0.Service.Impl;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Mapper.TouristMapper;
import com.example.end_0.Mapper.t_lMapper;
import com.example.end_0.Pojo.entity.Tourist;
import com.example.end_0.Service.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游客业务逻辑服务实现类
 * 实现TouristService接口，提供游客相关的具体业务逻辑
 * 包括游客信息管理、景点评分功能和微信登录功能
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@Service
public class TouristServiceImpl implements TouristService {

    @Autowired
    TouristMapper touristMapper;

    @Autowired
    t_lMapper tlMapper;

    @Autowired
    RestTemplate restTemplate;

    @Value("${wechat.app-id}")
    private String wxAppId;

    @Value("${wechat.app-secret}")
    private String wxAppSecret;

    @Value("${wechat.api-url}")
    private String wxApiUrl;

    @Value("${wechat.dev-mode:false}")
    private boolean devMode;

    /**
     * 添加新游客
     * 统一的用户注册方法，支持手动注册和微信登录
     *
     * @param tourist 游客对象，包含所有必要信息
     * @return 操作结果，包含新用户的ID
     */
    @Override
    public Result addTourist(Tourist tourist) {
        try {
            // 设置默认登录类型
            if (tourist.getLogin_type() == null) {
                tourist.setLogin_type(tourist.getWx_openid() != null ? "wechat" : "manual");
            }

            // 根据登录类型进行不同的验证
            if ("manual".equals(tourist.getLogin_type())) {
                // 手动注册：检查用户名唯一性
                if (tourist.getTourist_name() == null || tourist.getTourist_name().trim().isEmpty()) {
                    return Result.error("用户名不能为空");
                }
                if (touristMapper.existsByNameForManualRegistration(tourist.getTourist_name())) {
                    return Result.error("用户名已注册");
                }
                if (tourist.getTourist_password() == null || tourist.getTourist_password().trim().isEmpty()) {
                    return Result.error("密码不能为空");
                }
            } else if ("wechat".equals(tourist.getLogin_type())) {
                // 微信登录：检查OpenID唯一性
                if (tourist.getWx_openid() == null || tourist.getWx_openid().trim().isEmpty()) {
                    return Result.error("微信OpenID不能为空");
                }
                if (touristMapper.existsByOpenId(tourist.getWx_openid())) {
                    return Result.error("该微信用户已注册");
                }

                // 设置默认昵称（如果为空）
                if (tourist.getTourist_name() == null || tourist.getTourist_name().trim().isEmpty()) {
                    String suffix = tourist.getWx_openid().substring(Math.max(0, tourist.getWx_openid().length() - 6));
                    tourist.setTourist_name("微信用户_" + suffix);
                }

                // 设置默认密码（使用OpenID前16位）
                if (tourist.getTourist_password() == null) {
                    tourist.setTourist_password(
                            tourist.getWx_openid().substring(0, Math.min(16, tourist.getWx_openid().length())));
                }
            }

            // 插入数据库
            touristMapper.addTourist(tourist);

            // 返回新创建的用户ID（通过@Options注解自动设置到tourist对象中）
            return Result.success(tourist.getTourist_id());

        } catch (Exception e) {
            return Result.error("创建用户失败：" + e.getMessage());
        }
    }

    /**
     * 删除游客
     * 从数据库中删除指定的游客记录
     *
     * @param tourist_id 要删除的游客ID
     */
    @Override
    public void deleteTourist(Integer tourist_id) {
        touristMapper.deleteTourist(tourist_id);
    }

    /**
     * 更新游客信息
     * 更新数据库中的游客信息
     *
     * @param tourist 包含更新信息的游客对象
     */
    @Override
    public void updateTourist(Tourist tourist) {
        touristMapper.updateTourist(tourist);
    }

    /**
     * 获取所有游客信息
     * 从数据库查询所有游客的信息列表
     *
     * @return 游客信息列表
     */
    @Override
    public List<Tourist> getAllTourist() {
        return touristMapper.getAllTourist();
    }

    /**
     * 根据ID获取游客信息
     * 从数据库查询指定ID的游客信息
     *
     * @param tourist_id 游客ID
     * @return 游客信息对象
     */
    @Override
    public Tourist getTourist(Integer tourist_id) {
        return touristMapper.getTourist(tourist_id);
    }

    /**
     * 根据用户名获取游客ID
     * 通过用户名查找对应的游客ID
     *
     * @param tourist_name 游客用户名
     * @return 对应的游客ID
     */
    @Override
    public Integer getIdByName(String tourist_name) {
        return touristMapper.getIdByName(tourist_name);
    }

    /**
     * 根据微信OpenID获取游客信息
     * 通过微信OpenID查找对应的游客信息，用于微信登录
     *
     * @param wx_openid 微信OpenID
     * @return 操作结果，包含游客信息或错误信息
     */
    @Override
    public Result getTouristByOpenId(String wx_openid) {
        try {
            Tourist tourist = touristMapper.getTouristByOpenId(wx_openid);
            if (tourist != null) {
                return Result.success(tourist);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("查询失败：" + e.getMessage());
        }
    }

    /**
     * 获取微信用户信息
     * 根据微信code获取用户的openid和unionid
     * 开发环境返回虚拟数据，生产环境调用真实微信API
     *
     * @param code 微信登录返回的code
     * @return 操作结果，包含openid和unionid
     */
    @Override
    public Result getWxUserInfo(String code) {
        // 开发环境：返回虚拟数据
        if (devMode) {
            return generateDevWxUserInfo(code);
        }

        try {
            // 构建请求URL
            String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                    wxApiUrl, wxAppId, wxAppSecret, code);

            // 发送HTTP请求
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.get("openid") != null) {
                // 获取session_key，但不立即保存，等登录或注册时再保存
                return Result.success(response);
            } else {
                return Result.error("获取微信用户信息失败");
            }
        } catch (Exception e) {
            return Result.error("调用微信API失败：" + e.getMessage());
        }
    }

    /**
     * 生成开发环境的虚拟微信用户信息
     * 根据code生成对应的虚拟用户数据，方便前端测试
     *
     * @param code 微信登录返回的code
     * @return 操作结果，包含虚拟的openid和unionid
     */
    private Result generateDevWxUserInfo(String code) {
        Map<String, Object> result = new HashMap<>();

        // 生成固定的虚拟数据
        int hash = Math.abs(code.hashCode());
        int userIndex = hash % 3;

        switch (userIndex) {
            case 0:
                result.put("openid", "dev_openid_" + String.format("%03d", hash % 1000));
                result.put("unionid", "dev_unionid_" + String.format("%03d", hash % 1000));
                break;
            case 1:
                result.put("openid", "dev_openid_" + String.format("%03d", (hash + 1) % 1000));
                result.put("unionid", "dev_unionid_" + String.format("%03d", (hash + 1) % 1000));
                break;
            case 2:
                result.put("openid", "dev_openid_" + String.format("%03d", (hash + 2) % 1000));
                result.put("unionid", null);
                break;
            default:
                result.put("openid", "dev_openid_default");
                result.put("unionid", "dev_unionid_default");
                break;
        }

        // 生成session_key
        String devSessionKey = generateDevSessionKey(hash);
        result.put("session_key", devSessionKey);

        System.out.println("=== 开发环境虚拟微信用户信息 ===");
        System.out.println("输入code: " + code);
        System.out.println("返回openid: " + result.get("openid"));
        System.out.println("返回unionid: " + result.get("unionid"));
        System.out.println("返回session_key: " + result.get("session_key"));
        System.out.println("================================");

        return Result.success(result);
    }

    /**
     * 生成开发环境的虚拟session_key
     * 模拟微信的session_key格式（32字节Base64编码）
     *
     * @param hash 基于code的hash值
     * @return Base64编码的session_key
     */
    private String generateDevSessionKey(int hash) {
        // 创建32字节的数据（微信session_key长度）
        byte[] keyBytes = new byte[32];

        // 使用hash值填充数据，确保相同hash生成相同session_key
        for (int i = 0; i < 32; i++) {
            keyBytes[i] = (byte) ((hash + i) % 256);
        }

        // Base64编码
        return java.util.Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * 微信登录或注册
     * 统一处理微信登录和注册逻辑
     *
     * @param wxInfo 微信用户信息
     * @return 操作结果，包含用户信息
     */
    @Override
    public Result wechatLoginOrRegister(Map<String, Object> wxInfo) {
        try {
            String wxOpenid = (String) wxInfo.get("wx_openid");
            String wxUnionid = (String) wxInfo.get("wx_unionid");
            String nickname = (String) wxInfo.get("nickname");
            String avatarUrl = (String) wxInfo.get("avatar_url");
            String sessionKey = (String) wxInfo.get("session_key"); // 添加session_key

            // 开发环境下的默认值设置
            if (devMode) {
                if (nickname == null || nickname.trim().isEmpty()) {
                    String suffix = wxOpenid.substring(Math.max(0, wxOpenid.length() - 6));
                    nickname = "开发用户_" + suffix;
                }
                if (avatarUrl == null || avatarUrl.trim().isEmpty()) {
                    avatarUrl = "";
                }
                // 开发环境下生成session_key
                if (sessionKey == null || sessionKey.trim().isEmpty()) {
                    int hash = Math.abs(wxOpenid.hashCode());
                    sessionKey = generateDevSessionKey(hash);
                }
            }

            // 检查用户是否已存在
            Tourist existingTourist = touristMapper.getTouristByOpenId(wxOpenid);

            if (existingTourist != null) {
                // 用户已存在，更新session_key
                if (sessionKey != null) {
                    java.time.LocalDateTime expireTime = java.time.LocalDateTime.now().plusHours(2);
                    updateSessionKey(existingTourist.getTourist_id(), sessionKey, expireTime);
                }

                Map<String, Object> result = new HashMap<>();
                result.put("tourist_id", existingTourist.getTourist_id());
                result.put("tourist_name", existingTourist.getTourist_name());
                result.put("user_pic", existingTourist.getUser_pic());
                result.put("wx_openid", existingTourist.getWx_openid());
                result.put("wx_unionid", existingTourist.getWx_unionid());
                result.put("is_new_user", false);

                if (devMode) {
                    System.out.println("=== 开发环境登录信息 ===");
                    System.out.println("用户已存在，直接登录");
                    System.out.println("用户ID: " + existingTourist.getTourist_id());
                    System.out.println("用户名: " + existingTourist.getTourist_name());
                    System.out.println("已更新session_key: " + sessionKey);
                    System.out.println("======================");
                }

                return Result.success(result);
            } else {
                // 用户不存在，创建新用户
                Tourist newTourist = new Tourist();
                newTourist.setTourist_name(nickname);
                newTourist.setUser_pic(avatarUrl);
                newTourist.setWx_openid(wxOpenid);
                newTourist.setWx_unionid(wxUnionid);
                newTourist.setLogin_type("wechat");

                Result addResult = addTourist(newTourist);
                if (addResult.getCode() == 1) {
                    // 创建成功，保存session_key
                    Integer newUserId = (Integer) addResult.getData();
                    if (sessionKey != null) {
                        java.time.LocalDateTime expireTime = java.time.LocalDateTime.now().plusHours(2);
                        updateSessionKey(newUserId, sessionKey, expireTime);
                    }

                    Map<String, Object> result = new HashMap<>();
                    result.put("tourist_id", newUserId);
                    result.put("tourist_name", newTourist.getTourist_name());
                    result.put("user_pic", newTourist.getUser_pic());
                    result.put("wx_openid", newTourist.getWx_openid());
                    result.put("wx_unionid", newTourist.getWx_unionid());
                    result.put("is_new_user", true);

                    if (devMode) {
                        System.out.println("=== 开发环境注册信息 ===");
                        System.out.println("创建新用户成功");
                        System.out.println("用户ID: " + newUserId);
                        System.out.println("用户名: " + newTourist.getTourist_name());
                        System.out.println("已保存session_key: " + sessionKey);
                        System.out.println("======================");
                    }

                    return Result.success(result);
                } else {
                    return Result.error("创建用户失败：" + addResult.getData());
                }
            }
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }

    /**
     * 更新用户session_key
     * 保存微信登录时获取的session_key
     *
     * @param tourist_id 用户ID
     * @param sessionKey 会话密钥
     * @param expireTime 过期时间
     */
    @Override
    public void updateSessionKey(Integer tourist_id, String sessionKey, java.time.LocalDateTime expireTime) {
        touristMapper.updateSessionKey(tourist_id, sessionKey, expireTime);
    }

    /**
     * 游客给景点评分
     * 处理游客对景点的评分逻辑，包括重复评分检查
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @param score        评分值
     * @return 操作结果，成功或失败信息
     */
    @Override
    public Result score(Integer tourist_id, Integer landscape_id, Integer score) {
        if (tlMapper.findScoring(tourist_id, landscape_id) != null)
            return Result.error("该用户已为该景点打过分");
        tlMapper.addScoring(tourist_id, landscape_id, score);
        return Result.success();
    }

    /**
     * 删除游客评分
     * 删除游客对指定景点的评分记录
     *
     * @param tourist_id   游客ID
     * @param landscape_id 景点ID
     * @return 操作结果，成功或失败信息
     */
    @Override
    public Result deleteScoring(Integer tourist_id, Integer landscape_id) {
        if (tlMapper.findScoring(tourist_id, landscape_id) == null)
            return Result.error("该用户未为该景点打过分");
        tlMapper.deleteScoring(tourist_id, landscape_id);
        return Result.success();
    }
}