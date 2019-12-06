package com.food.sas.enums;

/**
 * @author Created by ygdxd_admin at 2019-12-06 10:33 PM
 */
public enum UserStatusEnum {
    /**
     * 冻结
     */
    FREEZING(1),
    /**
     * 正常
     */
    NORMAL(0);

    private Integer code;

    UserStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
