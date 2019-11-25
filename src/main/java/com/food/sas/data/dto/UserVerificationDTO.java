package com.food.sas.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-11-25 10:32 PM
 */
@Getter
@Setter
@ToString
public class UserVerificationDTO implements Serializable {

    private static final long serialVersionUID = 6001403612110253342L;

    private Integer id;

    private Long userId;

    private String path;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime auditTime;

    private Integer status;
}
