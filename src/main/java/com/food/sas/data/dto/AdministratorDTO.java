package com.food.sas.data.dto;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-11-04 9:00 PM
 */
public class AdministratorDTO implements Serializable {

    private Integer id;

    private String username;

    private String name;

    private String phone;

    private LocalDateTime createDate;

    private Integer status;
}
