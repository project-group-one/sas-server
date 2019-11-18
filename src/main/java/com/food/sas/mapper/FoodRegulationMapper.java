package com.food.sas.mapper;

import com.food.sas.data.dto.FoodRegulationDTO;
import com.food.sas.data.entity.FoodRegulation;
import org.mapstruct.Mapper;

/**
 * @author Created by ygdxd_admin at 2019-11-18 6:48 PM
 */
@Mapper
public interface FoodRegulationMapper {

    FoodRegulation toEntity(FoodRegulationDTO dto);

    FoodRegulationDTO fromEntity(FoodRegulation entity);

}
