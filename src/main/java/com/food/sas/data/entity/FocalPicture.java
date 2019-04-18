package com.food.sas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2019-01-08 9:44 PM
 */
@Data
@Table(name = "t_focal_picture")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FocalPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imgUrl;

    private Integer type;

    @CreatedDate
    private Date createDate;

    @CreatedBy
    private Integer creator;
}
