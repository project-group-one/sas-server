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

import java.util.Optional;

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
        Optional<FoodRegulation> optional = foodRegulationRepository.findOne(QFoodRegulation.foodRegulation.typeId.eq(body.getTypeId()));
        if (optional.isPresent()) {
            entity.setId(optional.get().getId());
        }
        foodRegulationRepository.saveAndFlush(entity);
        return entity.getId();
    }

    @Override
    public FoodRegulationDTO getFoodRegulation(Integer id) {
        Optional<FoodRegulation> optional = foodRegulationRepository.findOne(QFoodRegulation.foodRegulation.typeId.eq(id));
        if (optional.isPresent()) {
            return Mappers.getMapper(FoodRegulationMapper.class)
                    .fromEntity(optional.get());
        }
        return null;
    }

    @Override
    public Integer modifyFoodRegulation(FoodRegulationDTO body) {
        return null;
    }
}
