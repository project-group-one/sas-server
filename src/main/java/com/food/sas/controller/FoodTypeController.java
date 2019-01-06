package com.food.sas.controller;

import com.food.sas.data.dto.FoodTypeModel;
import com.food.sas.data.dto.FoodTypeRequest;
import com.food.sas.service.FoodTypeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by zj on 2019/1/6
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/food-types")
public class FoodTypeController {

    private final FoodTypeService foodTypeService;

    @ApiOperation("创建食品类别")
    @PostMapping
    public Mono<Void> createFoodType(@RequestBody FoodTypeRequest request) {
        return foodTypeService.createFoodType(request);
    }

    @ApiOperation("食品类别树")
    @GetMapping
    public Flux<FoodTypeModel> queryFoodType() {
        return foodTypeService.queryFoodType();
    }
}
