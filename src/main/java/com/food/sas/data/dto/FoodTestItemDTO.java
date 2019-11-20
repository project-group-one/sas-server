package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:19 PM
 */
@Data
public class FoodTestItemDTO implements Serializable {

    private static final long serialVersionUID = 1708302258604692872L;
    private Integer id;

    private Integer regulationId;

    private Integer no;

    private String project;

    private Integer type;

    private String demand;

    private String rule;

    private String method;
}
