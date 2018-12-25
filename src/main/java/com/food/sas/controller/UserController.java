package com.food.sas.controller;

import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.dto.UserDTO;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:15 PM
 */
@RestController
@RequestMapping("/user")
@Api("用户管理")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiModelProperty("用户列表查询")
    public BaseResult<?> getUserPages(UserDTO dto, @RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<UserDTO> result = userService.getUserPage(dto, PageRequest.of(page - 1 < 0 ? 0 : page, size));
        return new BaseResult<List<UserDTO>>(result.getContent(), result);
    }

    @PutMapping
    public Mono<?> registeredUser(@RequestBody UserDTO dto) {
        return Mono.empty();
    }


}
