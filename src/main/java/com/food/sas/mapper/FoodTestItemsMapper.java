package com.food.sas.mapper;

import com.food.sas.data.dto.FoodTestItemDTO;
import com.food.sas.data.entity.FoodTestItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-11-20 10:33 PM
 */
@Mapper
public interface FoodTestItemsMapper {

    FoodTestItemsMapper MAPPER = Mappers.getMapper(FoodTestItemsMapper.class);

    List<FoodTestItem> toEntitys(List<FoodTestItemDTO> dtos);
}
