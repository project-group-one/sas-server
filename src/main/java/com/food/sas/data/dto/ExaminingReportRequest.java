package com.food.sas.data.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("报告名称")
    private String name;

    @ApiModelProperty("报告路径")
    private String path;

    @ApiModelProperty("检测结果")
    private String examiningResult;

    @ApiModelProperty("检测评价")
    private String evaluation;

    @ApiModelProperty("指标")
    private String indicator;

}
