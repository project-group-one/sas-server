package com.food.sas.data.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Created by ygdxd_admin at 2019-11-04 8:13 PM
 */
@Data
public class ReportTypeDTO implements Serializable {

    private static final long serialVersionUID = 6704602003789016461L;

    private Integer id;

    private String name;

    private LocalDateTime createTime;

    private Long creator;


}
