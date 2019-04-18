package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:22 PM
 */
@Data
public class OrganizationDTO implements Serializable {

    private static final long serialVersionUID = 1357992997829500968L;

    private Long id;

    private String name;

    private Date createDate;

}
