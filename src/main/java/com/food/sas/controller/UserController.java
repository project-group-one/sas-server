package com.food.sas.controller;

import com.alibaba.fastjson.JSON;
import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.dto.UserDTO;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:15 PM
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {


    @Autowired
    private IUserService userService;

//    @Autowired
//    private MyMapReactiveUserDetailsService userDetailsService;

    //    @PreAuthorize("hasRole('ADMIN')")
    @ApiModelProperty("用户列表查询")
    public BaseResult<?> getUserPages(UserDTO dto, @RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "size", defaultValue = "20") int size) {
//        System.out.println(JSON.toJSONString(authentication.getAuthorities()));
        Page<UserDTO> result = userService.getUserPage(dto, PageRequest.of(page - 1 < 0 ? 0 : page, size));
        return new BaseResult<List<UserDTO>>(result.getContent(), result);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("添加用户")
    @PostMapping
    public Mono<?> registeredUser(@ApiParam(value = "创建用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Integer id, @RequestBody UserDTO dto) {

        if (!userService.validate(id, dto.getType())) {
            return Mono.error(new IllegalArgumentException("type is illegal"));
        }
        if (userService.createUser(dto)) {
//            userDetailsService.
        }
        return Mono.empty();
    }

    @ApiOperation("修改用户")
    @PutMapping("/{mId}")
    public Mono<?> modifyManager(@RequestBody UserDTO dto, @ApiParam("需要修改的用户的id") @PathVariable Integer mId, @ApiParam(value = "修改用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Integer id) {
        if (!userService.validateById(id, mId)) {
            return Mono.error(new IllegalArgumentException("id is illegal"));
        }
        if (dto.getType() != null && !userService.validate(id, dto.getType())) {
            return Mono.error(new IllegalArgumentException("type is illegal"));
        }
        userService.modifyManager(dto, mId);
        return Mono.empty();
    }

    @ApiOperation("冻结用户")
    @PostMapping("/{mId}/freeze")
    public Mono<?> freezeUser(@ApiParam(value = "冻结用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Integer id, @ApiParam("需要冻结的用户id") @PathVariable Integer mId) {
        if (!userService.validateById(id, mId)) {
            return Mono.error(new IllegalArgumentException("type is illegal"));
        }
        userService.freezeUser(mId);
        return Mono.empty();
    }


    @ApiOperation("删除管理员")
    @DeleteMapping
    public Mono<?> deleteUser(@ApiParam("id以逗号分隔") @RequestParam Integer[] ids, @ApiParam(value = "创建用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Integer id) {
        for (Integer mId : ids) {
            if (!userService.validateById(id, mId)) {
                return Mono.error(new IllegalArgumentException("type is illegal"));
            }
        }
        userService.batchDeleteUser(ids);
        return Mono.empty();
    }


}
