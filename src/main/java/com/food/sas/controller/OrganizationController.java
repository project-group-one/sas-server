package com.food.sas.controller;

import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.service.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:20 PM
 */
@RequestMapping("/api/organization")
@RestController
@Api(tags = "组织管理")
public class OrganizationController {

    private IOrganizationService service;

    @ApiOperation("查询组织")
    @GetMapping
    public BaseResult<List<OrganizationDTO>> searchOrganization(@RequestParam String name, @RequestParam(value = "current", defaultValue = "1") int page,
                                                                @RequestParam(value = "size", defaultValue = "20") int size) {

        Page<OrganizationDTO> result = service.searchOrganization(name, PageRequest.of(page - 1 < 0 ? 0 : page - 1, size));
        return new BaseResult<>(result.getContent(), result);
    }

    @ApiOperation("新增组织")
    @PostMapping
    public Mono<?> createOrganization(@RequestBody OrganizationDTO body) {
        service.createOrganization(body);
        return Mono.empty();
    }

    @ApiOperation("修改组织")
    @PutMapping("/{id}")
    public Mono<?> modifyOrganization(@PathVariable Integer id, @RequestBody OrganizationDTO body) {
        service.modifyOrganization(body, id);
        return Mono.empty();
    }

    @ApiOperation("删除组织")
    public Mono<?> deleteOrganization(@ApiParam("ids") @RequestParam Integer[] ids) {
        service.batchDeleteOrganization(ids);
        return Mono.empty();
    }


}
