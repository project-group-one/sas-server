package com.food.sas.data.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-11-09 5:03 PM
 */
@Data
public class FoodRegulationDTO implements Serializable {

    private static final long serialVersionUID = -4989432889298728060L;

    private Integer id;

    private String range;

    private String category;

    private String samplingBasis;

    private String demand;

    private String attention;

    private String conclusion;

}
