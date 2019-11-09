package com.food.sas.data.response;

import lombok.Data;

/**
 * @author Created by ygdxd_admin at 2019-11-09 4:25 PM
 */
@Data
public class SimpleResponse {

    private String msg;

    private Integer code;

    private String data;

    public SimpleResponse() {
        this.code = 200;
    }

    public SimpleResponse(String msg, Integer code, String data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static SimpleResponse forbidden() {
        return new SimpleResponse("登录失败", 403, "账号或密码错误");
    }
}
