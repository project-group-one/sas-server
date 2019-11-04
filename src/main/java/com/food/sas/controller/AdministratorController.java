package com.food.sas.controller;

import com.food.sas.data.dto.AdministratorDTO;
import com.food.sas.data.dto.AdministratorRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author Created by ygdxd_admin at 2019-11-04 9:13 PM
 */
@RestController
@RequestMapping("/admin")
public class AdministratorController {


    @PostMapping("/register")
    public Mono<?> register(@Valid @RequestBody AdministratorRequest body) {


        return Mono.just("a");

    }
}
