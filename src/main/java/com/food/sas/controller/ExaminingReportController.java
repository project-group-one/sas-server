package com.food.sas.controller;

import com.food.sas.data.dto.DetectionResultModel;
import com.food.sas.data.dto.ExaminingReportRequest;
import com.food.sas.data.entity.ExaminingReport;
import com.food.sas.data.entity.User;
import com.food.sas.data.response.Result;
import com.food.sas.service.ExaminingReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by zj on 2018/12/20
 */
@Api(tags = "SAS")
@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ExaminingReportController {

    private final ExaminingReportService examiningReportService;

    @ApiOperation("检测报告列表")
    @GetMapping
    public Result<List<ExaminingReport>> listExaminingReports(@RequestParam(value = "name", required = false) String name,
                                                              @RequestParam(value = "current", defaultValue = "1") int page,
                                                              @RequestParam(value = "pageSize", defaultValue = "20") int size,
                                                              @RequestParam(value = "orgId") Long orgId) {
        Page<ExaminingReport> reportPage = examiningReportService.listExaminingReports(orgId, name, page, size);
        return Result.success(reportPage.getContent(), reportPage);
    }

    @ApiOperation("检测报告详情")
    @GetMapping("/{id}")
    public Result<ExaminingReport> getExaminingReport(@PathVariable("id") Long id) {
        Optional<ExaminingReport> examiningReport = examiningReportService.getExaminingReport(id);
        return Result.success(examiningReport.orElse(null));
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
    public Result<DetectionResultModel> detectionReport(@PathVariable("id") Long id) {
        try {
            examiningReportService.detectionReport(id, "该报告检测成功！！！");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(new DetectionResultModel(true, "该报告检测成功！！！"));
    }

    @ApiOperation("删除检测报告")
    @DeleteMapping("/{ids}")
    public void deleteExaminingReport(@PathVariable("ids") @ApiParam("id以逗号分隔") List<Long> ids) {
        examiningReportService.deleteExaminingReport(ids);
    }
}
