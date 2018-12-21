package com.food.sas.controller;

import com.food.sas.dto.BaseResult;
import com.food.sas.dto.DetectionResultModel;
import com.food.sas.dto.ExaminingReportRequest;
import com.food.sas.entity.ExaminingReport;
import com.food.sas.service.ExaminingReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zj on 2018/12/20
 */
@Api(tags = "SAS")
@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ExaminingReportController {

    private final ExaminingReportService examiningReportService;

    @ApiOperation("检测报告列表")
    @GetMapping
    public BaseResult<List<ExaminingReport>> listExaminingReports(@RequestParam(value = "current", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<ExaminingReport> reportPage = examiningReportService.listExaminingReports(page, size);
        return new BaseResult<>(reportPage.getContent(), reportPage);
    }

    @ApiOperation("创建检测报告")
    @PostMapping
    public void createExaminingReport(@RequestBody ExaminingReportRequest request) {
        examiningReportService.createExaminingReport(request);
    }

    @ApiOperation("编辑检测报告")
    @PutMapping("/{id}")
    public void updateExaminingReport(@PathVariable("id") Long id, @RequestBody ExaminingReportRequest request) {
        examiningReportService.updateExaminingReport(id, request);
    }

    @ApiOperation("检测分析")
    @PatchMapping("/{id}/detection")
    public DetectionResultModel detectionReport(@PathVariable("id") Long id) {
        try {
            examiningReportService.detectionReport(id, "该报告检测成功！！！");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new DetectionResultModel(true, "该报告检测成功！！！");
    }

    @DeleteMapping("/{ids}")
    public void deleteExaminingReport(@PathVariable("ids") @ApiParam("id以逗号分隔") List<Long> ids) {
        examiningReportService.deleteExaminingReport(ids);
    }
}
