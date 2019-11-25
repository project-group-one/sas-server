package com.food.sas.service.impl;

import com.food.sas.data.dto.FoodTestItemDTO;
import com.food.sas.data.entity.FoodTestItem;
import com.food.sas.data.repository.FoodTestItemsRepository;
import com.food.sas.service.IFoodTestItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:23 PM
 */
@Service
public class FoodTestItemsServiceImpl implements IFoodTestItemsService {

    @Autowired
    private FoodTestItemsRepository foodTestItemsRepository;

    @Override
    public void saveFoodTestItems(List<FoodTestItem> items) {
//        foodTestItemsRepository.findAll(qfood)
//        foodTestItemsRepository.saveAll()
    }

    @Override
    public Flux<FoodTestItemDTO> searchFoodTestItems(Long typeId) {
        return null;
    }
}
