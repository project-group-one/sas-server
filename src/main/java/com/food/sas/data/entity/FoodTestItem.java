package com.food.sas.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:16 PM
 */
@Table(name = "t_food_test_items")
@Entity
@Data
public class FoodTestItem implements Serializable {


    private static final long serialVersionUID = -8585107150772812153L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer regulationId;

    private Integer no;

    private String project;

    private Integer type;

    private String demand;

    private String rule;

    private String method;
}
