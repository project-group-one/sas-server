package com.food.sas.service;

import com.food.sas.data.entity.FoodTestItem;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:22 PM
 */
public interface IFoodTestItemsService {

    void saveFoodTestItems(List<FoodTestItem> items);
}
