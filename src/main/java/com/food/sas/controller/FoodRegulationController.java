package com.food.sas.controller;

import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.dto.FoodRegulationDTO;
import com.food.sas.data.response.SimpleResponse;
import com.food.sas.service.IFoodRegulationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Created by ygdxd_admin at 2019-11-11 8:49 PM
 */
@Api("食品细则")
@RequestMapping("/food/regulation")
@RestController
public class FoodRegulationController {

    @Autowired
    private IFoodRegulationService foodRegulationService;

    @ApiOperation("获取食品细则详情")
    @GetMapping("/{id}")
    public Mono<?> searchFoodRegulation(@PathVariable Integer id) {
        FoodRegulationDTO result = foodRegulationService.getFoodRegulation(id);
        if (result == null) {
            return Mono.just(SimpleResponse.badRequest());
        }
        return Mono.just(result);
    }

    @ApiOperation("增加食品细则")
    @PostMapping
    public Mono<Integer> createFoodRegulation(@RequestBody FoodRegulationDTO body) {
        if (body.getTypeId() == null) {
            return Mono.error(new RuntimeException("请指定食品类别"));
        }
        return Mono.just(foodRegulationService.createFoodRegulation(body));
    }


}
