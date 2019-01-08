package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2019-01-08 9:46 PM
 */
@Data
public class FocalPictureDTO implements Serializable {

    private static final long serialVersionUID = 3751196782898863740L;

    private Integer id;

    private String name;

    private String imgUrl;

    private Integer type;

    private Date createDate;

    private Integer creator;
}
