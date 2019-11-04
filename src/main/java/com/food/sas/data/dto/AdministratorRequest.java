package com.food.sas.data.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Created by ygdxd_admin at 2019-11-04 9:16 PM
 */
@Data
public class AdministratorRequest implements Serializable {

    private static final long serialVersionUID = 3600804900291129941L;

    @Length(min = 8, message = "用户名不能小于8位")
    private String username;

    @Length(min = 8, message = "密码不能小于8位")
    @NotNull(message = "密码不能为空")
    private String password;
}
