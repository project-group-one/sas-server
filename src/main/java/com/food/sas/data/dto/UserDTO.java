package com.food.sas.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:18 PM
 */
@ApiModel
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 4770202285205940411L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("权限")
    private String role;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("电话")
    private String phone;
}
