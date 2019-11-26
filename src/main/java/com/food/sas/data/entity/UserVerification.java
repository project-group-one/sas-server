package com.food.sas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-11-25 10:17 PM
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserVerification implements Serializable {

    private static final long serialVersionUID = -1197782148769910290L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long userId;

    private String path;

    private String frontPath;

    private String backPath;

    private String remark;

    @Version
    private Integer version;

    @CreatedDate
    private LocalDateTime createTime;

    private LocalDateTime auditTime;

    private Integer status;
}
