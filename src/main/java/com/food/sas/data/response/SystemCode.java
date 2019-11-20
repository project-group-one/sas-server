package com.food.sas.data.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zj
 */

@Getter
@AllArgsConstructor
@ApiModel(description = "系统内置code")
public enum SystemCode implements IResultCode {
    /**
     * 系统未知异常
     */
    FAILURE(SystemCode.FAILURE_CODE, "系统未知异常"),
    /**
     * 操作成功
     */
    SUCCESS(SystemCode.SUCCESS_CODE, "操作成功"),
    /**
     * 404 没找到请求
     */
    NOT_FOUND(SystemCode.NOT_FOUND_CODE, "404 没找到请求"),
    /**
     * 请求被拒绝
     */
    REQ_REJECT(SystemCode.REQ_REJECT_CODE, "请求被拒绝"),

    BAD_REQUEST(SystemCode.BAD_REQUEST_CODE, "请求不正确"),

    PERMISSION_DENIED(SystemCode.PERMISSION_DENIED_CODE, "无权限请求"),
    ;
    /**
     * 通用 异常 code
     */
    public static final int FAILURE_CODE = -1;
    public static final int SUCCESS_CODE = 1;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int PERMISSION_DENIED_CODE = 403;
    public static final int NOT_FOUND_CODE = 404;
    public static final int REQ_REJECT_CODE = 500;

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String msg;
}