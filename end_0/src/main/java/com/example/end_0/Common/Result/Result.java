package com.example.end_0.Common.Result;

import com.example.end_0.Common.Constant.ResultConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API返回结果封装类
 * 用于标准化所有API接口的返回格式，包含状态码、消息和数据
 * 采用泛型设计，支持返回任意类型的数据
 *
 * @param <T> 返回数据的类型
 * @author system
 * @version 1.0
 * @since 2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 响应状态码
     * 1表示成功，0表示失败
     */
    private Integer code;

    /**
     * 响应消息
     * 成功时通常为"success"，失败时为具体的错误信息
     */
    private String msg;

    /**
     * 响应数据
     * 成功时包含具体的业务数据，失败时通常为null
     */
    private T data;

    /**
     * 创建成功响应（无数据）
     * 用于只需要返回成功状态，不需要返回具体数据的场景
     *
     * @param <T> 数据类型
     * @return 成功的Result对象，data为null
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = ResultConstant.SUCCESSCODE;
        result.msg = ResultConstant.SUCCESSMSG;
        result.data = null;
        return result;
    }

    /**
     * 创建成功响应（包含数据）
     * 用于需要返回具体业务数据的成功场景
     *
     * @param <T>  数据类型
     * @param data 要返回的业务数据
     * @return 成功的Result对象，包含指定的数据
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.code = ResultConstant.SUCCESSCODE;
        result.msg = ResultConstant.SUCCESSMSG;
        result.data = data;
        return result;
    }

    /**
     * 创建失败响应
     * 用于API调用失败的场景，返回具体的错误信息
     *
     * @param <T>     数据类型
     * @param message 具体的错误信息
     * @return 失败的Result对象，包含错误信息，data为null
     */
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<T>();
        result.code = ResultConstant.FAILCODE;
        result.msg = message;
        result.data = null;
        return result;
    }
}
