package com.food.sas.controller;

import com.food.sas.data.dto.FoodTypeModel;
import com.food.sas.data.dto.FoodTypeRequest;
import com.food.sas.data.response.R;
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

    @GetMapping("/count")
    public Flux<String> countFoodType() {
        return Flux.just("1");
    }


    @ApiOperation("食品类别树")
    @GetMapping
    public R<Flux<FoodTypeModel>> queryFoodType() {
        return R.success(foodTypeService.queryFoodType());
    }

    @ApiOperation("修改食品树")
    @PutMapping("/{id}")
    public R<Mono<Long>> modifyFoodType(@RequestBody FoodTypeRequest body) {
        return R.success(foodTypeService.modifyFoodType(body));
    }
}
