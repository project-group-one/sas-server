package com.food.sas.controller;

import com.food.sas.data.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:15 PM
 */
@RestController
@RequestMapping("/user")
@Api("用户管理")
public class UserController {

    @ApiModelProperty("用户列表查询")
    public Mono<?> getUserPages(UserDTO dto, @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "20") int size) {

        return Mono.empty();
    }

    @PutMapping
    public Mono<?> registeredUser(@RequestBody UserDTO dto) {
        return Mono.empty();
    }


}
