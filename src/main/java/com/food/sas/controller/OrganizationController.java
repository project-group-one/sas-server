package com.food.sas.controller;

import com.food.sas.data.dto.AddUserToOrganizationRequest;
import com.food.sas.data.dto.CreateOrganizationRequest;
import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.data.dto.OrganizationModel;
import com.food.sas.data.response.R;
import com.food.sas.enums.StatusEnum;
import com.food.sas.service.IOrganizationService;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:20 PM
 */
@RequestMapping("/api/organization")
@RestController
@Api(tags = "组织管理")
public class OrganizationController {

    @Autowired
    private IOrganizationService service;

    @Autowired
    private IUserService userService;

    @ApiOperation("根据id查询组织")
    @GetMapping("/{id}")
    public R<OrganizationDTO> searchOrganization(@PathVariable Long id) {
        return R.success(service.searchOrganization(id));
    }

    @ApiOperation("查询组织")
    @GetMapping
    public R<List<OrganizationModel>> searchOrganization(@RequestParam(required = false) String name, @RequestParam(value = "current", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "20") int size) {

        Page<OrganizationModel> result = service.searchOrganization(name, PageRequest.of(Math.max(page - 1, 0), size));
        return R.success(result.getContent(), result);
    }

    @ApiOperation("新增组织")
    @PostMapping
    public Mono<Void> createOrganization(@RequestBody @Valid CreateOrganizationRequest body, Authentication authentication) {
        Long creator = userService.findUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername()).getId();
        service.createOrganization(body, creator);
        return Mono.empty();
    }

    @ApiOperation("修改组织")
    @PutMapping("/{id}")
    public Mono<Void> modifyOrganization(@PathVariable Long id, @RequestBody CreateOrganizationRequest body) {
        service.modifyOrganization(body, id);
        return Mono.empty();
    }

    @ApiOperation("删除组织")
    @DeleteMapping("/{id}")
    public Mono<Void> deleteOrganization(@ApiParam("ids") @PathVariable("id") Long id) {
        service.batchDeleteOrganization(id);
        return Mono.empty();
    }

    @ApiOperation("向组织里面添加成员")
    @PostMapping("/users")
    public Mono<Void> addUserToOrganization(@RequestBody @Valid AddUserToOrganizationRequest request) {
        service.addUserToOrganization(request);
        return Mono.empty();
    }

    @ApiOperation("组织审核")
    @PutMapping("/{id}/audit")
    public Mono<Void> auditOrganization(@PathVariable("id") Long id,
                                        @RequestParam("status") StatusEnum status,
                                        @RequestParam(value = "errorMsg", required = false) String errorMsg) {
        service.auditOrganization(id, status, errorMsg);
        return Mono.empty();
    }
}
