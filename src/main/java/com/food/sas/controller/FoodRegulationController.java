package com.food.sas.controller;

import com.food.sas.data.dto.FoodRegulationDTO;
import com.food.sas.data.response.Result;
import com.food.sas.data.response.SystemCode;
import com.food.sas.service.FoodTypeService;
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
@RequestMapping("/api/food/regulation")
@RestController
public class FoodRegulationController {

    @Autowired
    private IFoodRegulationService foodRegulationService;

    @Autowired
    private FoodTypeService foodTypeService;

    @ApiOperation("获取食品细则详情")
    @GetMapping("/{id}")
    public Mono<Result<FoodRegulationDTO>> searchFoodRegulation(@PathVariable Integer id) {
        FoodRegulationDTO result = foodRegulationService.getFoodRegulation(id);
        return Mono.just(Result.success(result));
    }

    @ApiOperation("增加食品细则")
    @PostMapping
    public Mono<?> createFoodRegulation(@RequestBody FoodRegulationDTO body) {
        if (body.getTypeId() == null) {
            return Mono.just(Result.fail(SystemCode.BAD_REQUEST, "请指定食品类别"));
        }

        if (foodTypeService.getFoodType(Long.valueOf(body.getTypeId().toString())) == null) {
            return Mono.just(Result.fail(SystemCode.NOT_FOUND, "食品类别不存在"));
        }
        return Mono.just(foodRegulationService.createFoodRegulation(body));
    }


}
