package com.food.sas.mapper;

import com.food.sas.data.dto.ReportTypeDTO;
import com.food.sas.data.entity.ReportType;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-11-04 8:15 PM
 */
@Mapper
public interface ReportTypeMapper {

    ReportType toEntity(ReportTypeDTO dto);

    List<ReportTypeDTO> fromEntitys(List<ReportType> entitys);
}
