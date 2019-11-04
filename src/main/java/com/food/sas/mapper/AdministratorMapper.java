package com.food.sas.mapper;

import com.food.sas.data.dto.AdministratorDTO;
import com.food.sas.data.entity.Administrator;
import org.mapstruct.Mapper;

/**
 * @author Created by ygdxd_admin at 2019-11-04 9:00 PM
 */
@Mapper
public interface AdministratorMapper {

    Administrator toEntity(AdministratorDTO dto);

    AdministratorDTO fromEntity(Administrator entity);
}
