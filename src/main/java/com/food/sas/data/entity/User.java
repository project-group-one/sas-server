package com.food.sas.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2018-12-21 10:16 PM
 */
@Data
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = -6160174661276087435L;

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    /**
     * 0 普通用户 1 单位操作员 2 审核员 3 超级管理员
     */
    private Integer type;

    private String address;

    private String phone;

    private String role;

    @CreatedDate
    private Date createDate;

    private Integer orgizationId;

    private Integer status;
}
