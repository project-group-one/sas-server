package com.food.sas.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:20 PM
 */
@RequestMapping("/organization")
@RestController
@Api(tags = "组织管理")
public class OrganizationController {

    @ApiOperation("查询组织")
    @GetMapping
    public Mono<?> searchOrganization(@RequestParam String name, @PageableDefault Pageable pageable) {
        return Mono.empty();
    }
}
