package com.food.sas.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-04-19 9:23 PM
 */
@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String phone;

    private LocalDateTime expired;

    private Integer registered;
}
