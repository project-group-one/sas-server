package com.food.sas.data.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Created by ygdxd_admin at 2018-12-22 2:49 PM
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class News implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private Integer type;

    private String author;

    @CreatedDate
    private LocalDateTime releaseTime;

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
