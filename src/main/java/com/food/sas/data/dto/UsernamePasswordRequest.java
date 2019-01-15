package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-01-14 10:22 PM
 */
@Data
public class UsernamePasswordRequest implements Serializable {

    private static final long serialVersionUID = 2006599393912311187L;

    private String username;

    private String password;
}
