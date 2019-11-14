package com.food.sas.data.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author dong_gui
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 服务器当前时间戳
     */
    private Long ts = System.currentTimeMillis();

    /**
     * 成功数据
     */
    private T data;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String msg;

    public static Result ofSuccess() {
        Result result = new Result();
        result.success = true;
        return result;
    }

    public static Result ofSuccess(Object data) {
        Result result = new Result();
        result.success = true;
        result.setData(data);
        return result;
    }

    public static Result ofFail(Integer code, String msg) {
        Result result = new Result();
        result.success = false;
        result.code = code;
        result.msg = msg;
        return result;
    }

    public static Result ofFail(Integer code, String msg, Object data) {
        Result result = new Result();
        result.success = false;
        result.code = code;
        result.msg = msg;
        result.setData(data);
        return result;
    }
}