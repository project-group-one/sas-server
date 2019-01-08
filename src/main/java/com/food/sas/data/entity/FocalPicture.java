package com.food.sas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2019-01-08 9:44 PM
 */
@Data
@Table(name = "t_focal_picture")
@Entity
public class FocalPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String imgUrl;

    private Integer type;

    @CreatedDate
    private Date createDate;

    private Integer creator;
}
