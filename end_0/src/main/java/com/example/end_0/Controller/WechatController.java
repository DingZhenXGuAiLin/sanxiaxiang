package com.example.end_0.Controller;

import com.example.end_0.Common.Result.Result;
import com.example.end_0.Pojo.dto.DecryptPhoneRequest;
import com.example.end_0.Service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信功能控制器
 * 提供微信相关的REST API接口，包括手机号解密
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WechatService wechatService;

    /**
     * 微信手机号解密接口
     * 根据微信返回的加密数据解密获取真实手机号
     *
     * @param request 解密请求参数
     * @return 解密结果，包含手机号信息
     */
    @PostMapping("/decrypt-phone")
    public Result decryptPhone(@RequestBody DecryptPhoneRequest request) {
        return wechatService.decryptPhone(request);
    }
}