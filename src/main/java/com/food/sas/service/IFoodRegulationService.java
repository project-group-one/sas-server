package com.food.sas.service;

import com.food.sas.data.dto.FoodRegulationDTO;

/**
 * @author Created by ygdxd_admin at 2019-11-09 5:21 PM
 */
public interface IFoodRegulationService {

    Integer createFoodRegulation(FoodRegulationDTO body);

    FoodRegulationDTO getFoodRegulation(Integer id);

}
