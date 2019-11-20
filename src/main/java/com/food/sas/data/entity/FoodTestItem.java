package com.food.sas.data.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:16 PM
 */
@Data
public class FoodTestItem implements Serializable {


    private static final long serialVersionUID = -8585107150772812153L;

    private Integer id;

    private Integer regulationId;

    private Integer no;

    private String project;

    private Integer type;

    private String demand;

    private String rule;

    private String method;
}
