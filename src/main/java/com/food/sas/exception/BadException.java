package com.food.sas.exception;

import com.food.sas.data.response.IResultCode;
import com.food.sas.data.response.Result;
import com.food.sas.data.response.SystemCode;

import javax.annotation.Nullable;

/**
 * @author zj
 */
public class BadException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;

    @Nullable
    private final Result<?> result;

    public BadException(Result<?> result) {
        super(result.getMsg());
        this.result = result;
    }

    public BadException(IResultCode rCode) {
        this(rCode, rCode.getMsg());
    }

    public BadException(IResultCode rCode, String message) {
        super(message);
        this.result = Result.fail(rCode, message);
    }

    public BadException(String message) {
        super(message);
        this.result = Result.fail(SystemCode.BAD_REQUEST, message);
    }

    public BadException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public BadException(String message, Throwable cause) {
        super(message, cause);
        doFillInStackTrace();
        this.result = null;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> Result<T> getResult() {
        return (Result<T>) result;
    }

    /**
     * 提高性能
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }

}