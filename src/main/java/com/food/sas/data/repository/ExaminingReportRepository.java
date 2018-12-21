package com.food.sas.data.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.food.sas.data.entity.ExaminingReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zj on 2018/12/21
 */
@Repository
public interface ExaminingReportRepository extends EntityGraphJpaRepository<ExaminingReport, Long>, EntityGraphQuerydslPredicateExecutor<ExaminingReport> {

    void deleteByIdIn(List<Long> ids);
}
