package com.example.end_0.Common.Result;

import com.example.end_0.Common.Constant.ResultConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(){
        Result<T> result = new Result<T>();
        result.code = ResultConstant.SUCCESSCODE;
        result.msg= ResultConstant.SUCCESSMSG;
        result.data=null;
        return result;
    }

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<T>();
        result.code = ResultConstant.SUCCESSCODE;
        result.msg= ResultConstant.SUCCESSMSG;
        result.data=data;
        return result;
    }

    public static <T> Result<T> error(String message){
        Result<T> result = new Result<T>();
        result.code = ResultConstant.FAILCODE;
        result.msg= message;
        result.data=null;
        return result;
    }
}
