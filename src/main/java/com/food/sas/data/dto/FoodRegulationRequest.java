package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-11-12 10:37 PM
 */
@Data
public class FoodRegulationRequest implements Serializable {

    private static final long serialVersionUID = -6328756910830440209L;

    private String range;

    /**
     * 产品种类
     */
    private String category;

    private String samplingBasis;

    private String demand;

    private String attention;

    private String conclusion;
}
