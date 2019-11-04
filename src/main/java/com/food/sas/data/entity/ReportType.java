package com.food.sas.data.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报告类别
 *
 * @author Created by ygdxd_admin at 2019-11-04 8:09 PM
 */
@Data
@Entity
@Table(name = "t_report_type")
@EntityListeners(AuditingEntityListener.class)
public class ReportType implements Serializable {

    private static final long serialVersionUID = 9174152033979623373L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @CreatedDate
    private LocalDateTime createTime;

    @CreatedBy
    private Long creator;
}
