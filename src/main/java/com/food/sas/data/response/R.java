package com.food.sas.data.response;

import com.food.sas.exception.BadException;
import lombok.*;
import org.springframework.data.domain.Page;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author dong_gui
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class R<T> implements Serializable {

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

    private Pagination pagination;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String msg;


    @Setter
    @Getter
    static class Pagination {
        private Integer current;
        private Integer pageSize;
        private Integer total;

        Pagination(Integer current, Integer pageSize, Integer total) {
            this.current = current;
            this.pageSize = pageSize;
            this.total = total;
        }
    }

    private R(IResultCode resultCode) {
        this(resultCode, resultCode.getMsg(), null, null);
    }

    private R(IResultCode resultCode, String msg) {
        this(resultCode, msg, null, null);
    }

    private R(IResultCode resultCode, T data) {
        this(resultCode, resultCode.getMsg(), data, null);
    }

    public R(IResultCode resultCode, T data, Page page) {
        this(resultCode, resultCode.getMsg(), data, page);
    }

    private R(IResultCode resultCode, String msg, T data, Page page) {
        this.code = resultCode.getCode();
        this.msg = msg;
        this.data = data;
        this.success = SystemCode.SUCCESS == resultCode;
        if (Objects.nonNull(page)) {
            this.pagination = new Pagination(page.getNumber() + 1, page.getSize(), Integer.valueOf(String.valueOf(page.getTotalElements())));
        }
    }

    public static R forbidden() {
        return R.fail(SystemCode.REQ_REJECT, "账号或密码错误");
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable R<?> result) {
        return Optional.ofNullable(result)
                .map(r -> r.code)
                .map(code -> SystemCode.SUCCESS.code == code)
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(@Nullable R<?> result) {
        return !R.isSuccess(result);
    }

    /**
     * 获取data
     *
     * @param result Result
     * @param <T>    泛型标记
     * @return 泛型对象
     */
    @Nullable
    public static <T> T getData(@Nullable R<T> result) {
        return Optional.ofNullable(result)
                .filter(r -> r.success)
                .map(x -> x.data)
                .orElse(null);
    }

    /**
     * 返回成功
     *
     * @param <T> 泛型标记
     * @return Result
     */
    public static <T> R<T> success() {
        return new R<>(SystemCode.SUCCESS);
    }

    /**
     * 成功-携带数据
     *
     * @param data 数据
     * @param <T>  泛型标记
     * @return Result
     */
    public static <T> R<T> success(@Nullable T data) {
        return new R<>(SystemCode.SUCCESS, data);
    }


    /**
     * 成功-携带数据
     *
     * @param data 数据
     * @param <T>  泛型标记
     * @return Result
     */
    public static <T> R<T> success(@Nullable T data, Page page) {
        return new R<>(SystemCode.SUCCESS, data);
    }

    /**
     * 根据状态返回成功或者失败
     *
     * @param status 状态
     * @param msg    异常msg
     * @param <T>    泛型标记
     * @return Result
     */
    public static <T> R<T> status(boolean status, String msg) {
        return status ? R.success() : R.fail(msg);
    }

    /**
     * 根据状态返回成功或者失败
     *
     * @param status 状态
     * @param sCode  异常code码
     * @param <T>    泛型标记
     * @return Result
     */
    public static <T> R<T> status(boolean status, IResultCode sCode) {
        return status ? R.success() : R.fail(sCode);
    }

    /**
     * 返回失败信息，用于 web
     *
     * @param msg 失败信息
     * @param <T> 泛型标记
     * @return {Result}
     */
    public static <T> R<T> fail(String msg) {
        return new R<>(SystemCode.FAILURE, msg);
    }

    /**
     * 返回失败信息
     *
     * @param rCode 异常枚举
     * @param <T>   泛型标记
     * @return {Result}
     */
    public static <T> R<T> fail(IResultCode rCode) {
        return new R<>(rCode);
    }

    /**
     * 返回失败信息
     *
     * @param rCode 异常枚举
     * @param msg   失败信息
     * @param <T>   泛型标记
     * @return {Result}
     */
    public static <T> R<T> fail(IResultCode rCode, String msg) {
        return new R<>(rCode, msg);
    }

    /**
     * 当 result 不成功时：直接抛出失败异常，返回传入的 result。
     *
     * @param result R
     */
    public static void throwOnFail(R<?> result) {
        if (R.isNotSuccess(result)) {
            throw new BadException(result);
        }
    }

    /**
     * 当 result 不成功时：直接抛出失败异常，返回传入的 rCode
     *
     * @param result R
     * @param rCode  异常枚举
     */
    public static void throwOnFail(R<?> result, IResultCode rCode) {
        if (R.isNotSuccess(result)) {
            throw new BadException(rCode);
        }
    }

    /**
     * 当 result 不成功时：直接抛出失败异常，返回传入的 rCode、message
     *
     * @param result R
     * @param rCode  异常枚举
     * @param msg    失败信息
     */
    public static void throwOnFail(R<?> result, IResultCode rCode, String msg) {
        if (R.isNotSuccess(result)) {
            throw new BadException(rCode, msg);
        }
    }

    /**
     * 当 status 不为 true 时：直接抛出失败异常 rCode
     *
     * @param status status
     * @param rCode  异常枚举
     */
    public static void throwOnFalse(boolean status, IResultCode rCode) {
        if (!status) {
            throw new BadException(rCode);
        }
    }

    /**
     * 当 status 不为 true 时：直接抛出失败异常 rCode、message
     *
     * @param status status
     * @param rCode  异常枚举
     * @param msg    失败信息
     */
    public static void throwOnFalse(boolean status, IResultCode rCode, String msg) {
        if (!status) {
            throw new BadException(rCode, msg);
        }
    }

    /**
     * 直接抛出失败异常，抛出 code 码
     *
     * @param rCode IResultCode
     */
    public static void throwFail(IResultCode rCode) {
        throw new BadException(rCode);
    }

    /**
     * 直接抛出失败异常，抛出 code 码
     *
     * @param rCode   IResultCode
     * @param message 自定义消息
     */
    public static void throwFail(IResultCode rCode, String message) {
        throw new BadException(rCode, message);
    }
}