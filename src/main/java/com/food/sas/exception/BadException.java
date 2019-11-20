package com.food.sas.exception;

import com.food.sas.data.response.IResultCode;
import com.food.sas.data.response.R;

import javax.annotation.Nullable;

/**
 * @author zj
 */
public class BadException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;

    @Nullable
    private final R<?> result;

    public BadException(R<?> result) {
        super(result.getMsg());
        this.result = result;
    }

    public BadException(IResultCode rCode) {
        this(rCode, rCode.getMsg());
    }

    public BadException(IResultCode rCode, String message) {
        super(message);
        this.result = R.fail(rCode, message);
    }

    public BadException(String message) {
        super(message);
        this.result = null;
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
    public <T> R<T> getResult() {
        return (R<T>) result;
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