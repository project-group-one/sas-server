package com.food.sas.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-11-09 5:03 PM
 */
@ApiModel("食品细则")
@Data
public class FoodRegulationDTO implements Serializable {

    private static final long serialVersionUID = -4989432889298728060L;

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("适用范围")
    private String range;

    @ApiModelProperty("种类")
    private String category;

    @ApiModelProperty("抽检依据")
    private String samplingBasis;

    @ApiModelProperty("抽检要求")
    private String demand;

    @ApiModelProperty("注意事项")
    private String attention;

    @ApiModelProperty("判断原则")
    private String conclusion;

}
