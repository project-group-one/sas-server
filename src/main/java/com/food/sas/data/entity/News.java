package com.food.sas.data.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2018-12-22 2:49 PM
 */
@Entity
@Data
public class News implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private Integer type;

    private String author;

    @CreatedDate
    private Date releaseTime;

    private String keywords;

    private String summary;

    private String storeUrl;

    /**
     * lai yuan
     */
    private String source;

    private String imgUrl;

    private Integer viewCount;
}
