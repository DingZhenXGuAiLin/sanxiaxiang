package com.example.end_0.Common.Constant;

/**
 * API返回结果常量类
 * 定义了统一的API响应状态码和消息常量
 * 用于标准化所有API接口的返回格式
 *
 * @author system
 * @version 1.0
 * @since 2024
 */
public class ResultConstant {

    /**
     * 成功响应消息
     * 当API调用成功时返回的标准消息
     */
    public static String SUCCESSMSG = "success";

    /**
     * 失败响应消息
     * 当API调用失败时返回的默认消息（通常会被具体错误信息覆盖）
     */
    public static String FAILMSG = "fail";

    /**
     * 成功响应状态码
     * 表示API调用成功的状态码，值为1
     */
    public static int SUCCESSCODE = 1;

    /**
     * 失败响应状态码
     * 表示API调用失败的状态码，值为0
     */
    public static int FAILCODE = 0;
}
