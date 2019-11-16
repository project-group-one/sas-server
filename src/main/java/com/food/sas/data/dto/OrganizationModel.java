package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:22 PM
 */
@Data
public class OrganizationModel {

    private Long id;

    private String name;

    private Date createDate;

    private Long creator;

}
