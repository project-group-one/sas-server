package com.food.sas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by zj on 2018/12/21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExaminingReportRequest {

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

}
