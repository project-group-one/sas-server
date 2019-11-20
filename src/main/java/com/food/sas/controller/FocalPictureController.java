package com.food.sas.controller;

import com.food.sas.data.dto.FocalPictureDTO;
import com.food.sas.data.response.Result;
import com.food.sas.service.IFocalPictureService;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-08 9:36 PM
 */
@Api(tags = "焦点图")
@RequestMapping("/api/pic")
@RestController
public class FocalPictureController {

    @Autowired
    private IFocalPictureService service;

    @Autowired
    private IUserService userService;

    @ApiOperation("获取单个焦点图")
    @GetMapping("/{id}")
    public Result<FocalPictureDTO> searchFocalPicture(@PathVariable Long id) {
        return Result.success(service.searchFocalPicture(id));
    }


    @ApiOperation("查询焦点图")
    @GetMapping
    public Result<List<FocalPictureDTO>> searchFocalPicture(@RequestParam(required = false) String name, @RequestParam(value = "current", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<FocalPictureDTO> result = service.searchFocalPicture(name, PageRequest.of(Math.max(page - 1, 0), size));
        return Result.success(result.getContent(), result);
    }

    @ApiOperation("新增焦点图")
    @PostMapping
    public Mono<Void> createFocalPicture(@RequestBody FocalPictureDTO body, Authentication authentication) {
        System.out.println(((UserDetails) authentication.getPrincipal()).getUsername());
        body.setCreator(userService.findUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername()).getId());
        service.createFocalPicture(body);
        return Mono.empty();
    }

    @ApiOperation("修改焦点图 名称 路径")
    @PutMapping("/{id}")
    public Mono<Void> modifyFocalPicture(@RequestBody FocalPictureDTO body, @PathVariable Long id) {
        service.modifyFocalPicture(body, id);
        return Mono.empty();
    }

    @ApiOperation("删除焦点图")
    @DeleteMapping
    public Result<Boolean> deleteFocalPicture(@RequestParam Long[] ids) {
        service.batchDeleteFocalPicture(ids);
        return Result.success(true);
    }
}
