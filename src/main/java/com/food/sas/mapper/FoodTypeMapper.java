package com.food.sas.mapper;

import com.food.sas.data.dto.FoodTypeModel;
import com.food.sas.data.dto.FoodTypeRequest;
import com.food.sas.data.entity.FoodType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by zj on 2018/12/21
 */
@Mapper
public interface FoodTypeMapper {

    FoodTypeMapper MAPPER = Mappers.getMapper(FoodTypeMapper.class);

    FoodType toEntity(FoodTypeRequest request);

    List<FoodTypeModel> toModels(List<FoodType> foodTypes);
}
