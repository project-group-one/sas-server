package com.food.sas.service;

import com.food.sas.data.dto.ExaminingReportRequest;
import com.food.sas.data.entity.ExaminingReport;
import com.food.sas.data.entity.QExaminingReport;
import com.food.sas.data.repository.ExaminingReportRepository;
import com.food.sas.data.repository.FileInfoRepository;
import com.food.sas.mapper.ExaminingReportMapper;
import com.food.sas.util.PathUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.Files.deleteIfExists;

/**
 * Created by zj on 2018/12/21
 */
@Slf4j
@Service
@AllArgsConstructor
public class ExaminingReportService {

    private FileInfoRepository fileInfoRepository;
    private ExaminingReportRepository examiningReportRepository;

    public Page<ExaminingReport> listExaminingReports(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isEmpty(name)) {
            builder.and(QExaminingReport.examiningReport.name.contains(name));
        }
        return examiningReportRepository.findAll(builder, pageRequest);
    }

    public Optional<ExaminingReport> getExaminingReport(Long id) {
        return examiningReportRepository.findById(id);
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

    @Transactional
    public void deleteExaminingReport(List<Long> ids) {
        List<ExaminingReport> reports = examiningReportRepository.findByIdIn(ids);
        examiningReportRepository.deleteAll(reports);
        List<String> fileNames = reports.stream().map(ExaminingReport::getName).collect(Collectors.toList());
        deleteFile(fileNames);
    }

    @Async
    public void deleteFile(List<String> fileNames) {
        fileNames.forEach(name -> {
            try {
                deleteIfExists(Paths.get(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void detectionReport(Long id, String msg) {
        Optional<ExaminingReport> reportOptional = examiningReportRepository.findById(id);
        reportOptional.ifPresent(report -> {
            report.setExaminingResult(msg);
            examiningReportRepository.save(report);
        });
    }
}
