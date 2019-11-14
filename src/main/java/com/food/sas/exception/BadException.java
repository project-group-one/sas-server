package com.food.sas.exception;

/**
 * @author zj
 */
public class BadException extends RuntimeException {

    private Integer code;

    private String msg;

    public BadException() {
    }

    public BadException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BadException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
