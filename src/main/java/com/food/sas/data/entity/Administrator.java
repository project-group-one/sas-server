package com.food.sas.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-11-04 8:23 PM
 */
@Data
@Entity
@Table(name = "s_administrator")
@EntityListeners(AuditingEntityListener.class)
public class Administrator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @JsonIgnore
    private String password;

    private String name;

    private String phone;

    @CreatedDate
    private LocalDateTime createDate;

    private Integer status;
}
