package com.food.sas.controller;

import com.food.sas.service.IFoodRegulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Created by ygdxd_admin at 2019-11-11 8:49 PM
 */
@RequestMapping("/food/regulation")
@RestController
public class FoodRegulationController {

    @Autowired
    private IFoodRegulationService foodRegulationService;

    @PostMapping
    public Mono<Integer> createFoodRegulation() {
        return Mono.just(1);
    }
}
