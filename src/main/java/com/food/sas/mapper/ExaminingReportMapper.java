package com.food.sas.mapper;

import com.food.sas.data.dto.ExaminingReportRequest;
import com.food.sas.data.entity.ExaminingReport;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by zj on 2018/12/21
 */
@Mapper
public interface ExaminingReportMapper {

    ExaminingReportMapper MAPPER = Mappers.getMapper(ExaminingReportMapper.class);

    ExaminingReport toEntity(ExaminingReportRequest reportRequest);

}
