package com.food.sas.controller;

import com.food.sas.data.dto.FoodTestItemDTO;
import com.food.sas.data.response.SimpleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-11-18 8:20 PM
 */
@RequestMapping("/api/test-items")
@RestController
public class FoodTestItemsController {

    @PostMapping
    public Mono<?> createFoodTestItems(@RequestBody List<FoodTestItemDTO> body) {
        return Mono.just("succ");
    }
}
