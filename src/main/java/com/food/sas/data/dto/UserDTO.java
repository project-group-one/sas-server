package com.food.sas.data.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.food.sas.security.PasswordSerializer;
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

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @JsonSerialize(using = PasswordSerializer.class)
    private String password;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("类型 0 普通用户 1 单位操作员 2 审核员")
    private Integer type;

    @ApiModelProperty("权限")
    private String role;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("状态 0 正常 1 冻结")
    private Integer status;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("当前用户是否已审核")
    private boolean verified;

    @ApiModelProperty("是否冻结")
    private boolean locked;
}
