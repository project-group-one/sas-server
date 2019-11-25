package com.food.sas.controller;

import com.food.sas.data.dto.FoodTestItemDTO;
import com.food.sas.data.response.Result;
import com.food.sas.service.IFoodTestItemsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:20 PM
 */
@Api("食品检测项目")
@RequestMapping("/api/test-items")
@RestController
public class FoodTestItemsController {

    @Autowired
    private IFoodTestItemsService foodTestItemsService;

    @PostMapping
    public Mono<?> createFoodTestItems(@RequestBody List<FoodTestItemDTO> body) {
        return Mono.just("succ");
    }

    @GetMapping
    public Result<Mono<List<FoodTestItemDTO>>> searchFoodTestItems(@RequestParam Long typeId) {
        return Result.success(foodTestItemsService.searchFoodTestItems(typeId).collectList());
    }


}
