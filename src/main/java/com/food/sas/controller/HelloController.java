package com.food.sas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by zj on 2018/12/20
 */
@Api(tags = "SAS")
@RestController
public class HelloController {

    //@PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("hello！！！")
    @GetMapping("/hello")
    public Mono<String> hello(@RequestParam(value = "kk",required = false) String val){
        return Mono.just("hello");
    }
}
