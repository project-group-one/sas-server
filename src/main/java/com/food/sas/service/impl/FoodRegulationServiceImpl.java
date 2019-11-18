package com.food.sas.service.impl;

import com.food.sas.data.dto.FoodRegulationDTO;
import com.food.sas.data.entity.FoodRegulation;
import com.food.sas.data.entity.QFoodRegulation;
import com.food.sas.data.repository.FoodRegulationRepository;
import com.food.sas.data.repository.FoodTypeRepository;
import com.food.sas.mapper.FoodRegulationMapper;
import com.food.sas.service.IFoodRegulationService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by ygdxd_admin at 2019-11-09 5:21 PM
 */
@Service
public class FoodRegulationServiceImpl implements IFoodRegulationService {

    @Autowired
    private FoodRegulationRepository foodRegulationRepository;

    @Override
    public Integer createFoodRegulation(FoodRegulationDTO body) {
        FoodRegulation entity = Mappers.getMapper(FoodRegulationMapper.class).toEntity(body);
        foodRegulationRepository.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public FoodRegulationDTO getFoodRegulation(Integer id) {
        return Mappers.getMapper(FoodRegulationMapper.class)
                .fromEntity(foodRegulationRepository.findOne(QFoodRegulation.foodRegulation.id.eq(id)).get());
    }
}
