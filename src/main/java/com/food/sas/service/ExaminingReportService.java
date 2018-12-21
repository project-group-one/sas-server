package com.food.sas.service;

import com.food.sas.dto.ExaminingReportRequest;
import com.food.sas.entity.ExaminingReport;
import com.food.sas.entity.repository.ExaminingReportRepository;
import com.food.sas.mapper.ExaminingReportMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by zj on 2018/12/21
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExaminingReportService {

    private final ExaminingReportRepository examiningReportRepository;


    public Page<ExaminingReport> listExaminingReports(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return examiningReportRepository.findAll(pageRequest);
    }

    public void createExaminingReport(ExaminingReportRequest request) {
        ExaminingReport examiningReport = ExaminingReportMapper.MAPPER.toEntity(request);
        examiningReportRepository.save(examiningReport);
    }

    public void updateExaminingReport(Long id, ExaminingReportRequest request) {
        Optional<ExaminingReport> reportOptional = examiningReportRepository.findById(id);
        reportOptional.ifPresent(report -> {
            String evaluation = request.getEvaluation();
            if (!StringUtils.isEmpty(evaluation)) {
                report.setEvaluation(evaluation);
            }
            String name = request.getName();
            if (!StringUtils.isEmpty(name)) {
                report.setName(name);
            }
            examiningReportRepository.save(report);
        });
    }

    public void deleteExaminingReport(List<Long> ids) {
        examiningReportRepository.deleteByIdIn(ids);
    }

    public void detectionReport(Long id, String msg) {
        Optional<ExaminingReport> reportOptional = examiningReportRepository.findById(id);
        reportOptional.ifPresent(report -> {
            report.setExaminingResult(msg);
            examiningReportRepository.save(report);
        });
    }
}
