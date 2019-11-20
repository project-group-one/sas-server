package com.food.sas.data.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Created by ygdxd_admin at 2019-11-09 5:05 PM
 */
@Data
@Entity
@Table(name = "t_food_regulation")
public class FoodRegulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 适用范围
     */
    @Column
    private String ranged;

    /**
     * 产品种类
     */
    @Column
    private String category;

    /**
     * 抽检依据
     */
    @Column(length = 2047)
    private String samplingBasis;

    /**
     * 抽检要求
     */
    @Column(length = 2047)
    private String demand;

    /**
     * 注意事项
     */
    @Column(length = 2047)
    private String attention;

    /**
     * 判断原则与结论
     */
    @Column(length = 2047)
    private String conclusion;


    private Integer typeId;


//    private
}
