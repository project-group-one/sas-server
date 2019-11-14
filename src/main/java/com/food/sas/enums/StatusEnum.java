package com.food.sas.enums;

/**
 * @author dong_gui on 2019/11/14.
 */
public enum StatusEnum {

    /**
     *
     */
    WAIT_AUDIT("待审核"),
    AUDITING("审核中"),
    FAIL("审核失败"),
    SUCCESS("审核通过");

    private String desc;

    StatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
