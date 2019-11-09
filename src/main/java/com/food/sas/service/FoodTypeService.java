package com.food.sas.service;

import com.food.sas.data.dto.FoodTypeModel;
import com.food.sas.data.dto.FoodTypeRequest;
import com.food.sas.data.entity.FoodType;
import com.food.sas.data.repository.FoodTypeRepository;
import com.food.sas.mapper.FoodTypeMapper;
import com.food.sas.util.TreeHelper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * Created by zj on 2019/1/5
 */
@Service
@Log4j2
@AllArgsConstructor
public class FoodTypeService {

    private final FoodTypeRepository foodTypeRepository;

    public Mono<Void> createFoodType(FoodTypeRequest request) {
        FoodType foodType = FoodTypeMapper.MAPPER.toEntity(request);
        Optional<FoodType> typeOptional = foodTypeRepository.findById(request.getParentId());
        if (typeOptional.isPresent()) {
            FoodType type = typeOptional.get();
            String path = type.getPath() == null ? type.getId() + "-" : type.getPath() + type.getId() + "-";
            foodType.setLevel(type.getLevel() + 1);
            foodType.setPath(path);
            foodTypeRepository.save(foodType);
        } else {
            foodTypeRepository.save(foodType);
        }
        return Mono.empty();
    }

    public Flux<FoodTypeModel> queryFoodType() {
        List<FoodType> foodTypes = foodTypeRepository.findAll();
        List<FoodTypeModel> foodTypeModels = FoodTypeMapper.MAPPER.toModels(foodTypes);
        List<FoodTypeModel> typeModels = TreeHelper.buildTree(-1L, foodTypeModels);
        return Flux.fromIterable(typeModels);
    }
}
