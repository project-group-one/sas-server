package com.food.sas.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2018-12-21 10:16 PM
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    private static final long serialVersionUID = -6160174661276087435L;

    @Id
    @GeneratedValue
    private Long id;

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
    private LocalDateTime createDate;

    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Organization organization;

    @ColumnDefault("0")
    private Integer verifyStatus;

}
