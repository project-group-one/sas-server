package com.food.sas.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

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

    private String username;

    @JsonIgnore
    private String password;

    private Integer type;

    private String address;

    private String role;
}
