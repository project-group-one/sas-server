package com.food.sas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:05 PM
 */
@Table(name = "t_organization")
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Organization implements Serializable {

    private static final long serialVersionUID = -2067391799785799688L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @CreatedDate
    private LocalDateTime createDate;

    @CreatedBy
    private Integer creator;


}
