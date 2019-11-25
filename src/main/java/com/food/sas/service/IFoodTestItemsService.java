package com.food.sas.service;

import com.food.sas.data.dto.FoodTestItemDTO;
import com.food.sas.data.entity.FoodTestItem;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:22 PM
 */
public interface IFoodTestItemsService {

    void saveFoodTestItems(List<FoodTestItem> items);

    Flux<FoodTestItemDTO> searchFoodTestItems(Long typeId);
}
