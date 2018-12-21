package com.food.sas.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zj on 2018/12/21
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ExaminingReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String path;

    /**
     * 检测结果
     */
    private String examiningResult;

    /**
     * 检测评价
     */
    private String evaluation;

    /**
     * 指标
     */
    private String indicator;

    @Version
    private Long version;

    @CreatedDate
    private Date createdDate;


}
