package com.food.sas.controller;

import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.dto.UserDTO;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:15 PM
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
public class UserController {

    private static final String SALT = "$qiqi";

    private static final String ROLE = "NORMAL";

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IUserService userService;

    @Autowired
    private MyMapReactiveUserDetailsService userDetailsService;

    @ApiOperation("登录后调用 获取必要参数")
    @GetMapping("/current")
    public Mono<?> getCurrentUser(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return Mono.just(userService.findUserByUsername(authentication.getName()));
        }
        return Mono.error(new AccessDeniedException("illegal operation"));
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("用户列表查询 查询参数 username(半模糊) address type name(半模糊)")
    @GetMapping
    public BaseResult<List<UserDTO>> getUserPages(UserDTO dto, @RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<UserDTO> result = userService.getUserPage(dto, PageRequest.of(page - 1 < 0 ? 0 : page - 1, size));
        return new BaseResult<>(result.getContent(), result);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("添加用户")
    @PostMapping
    public Mono<?> registeredUser(@ApiParam(value = "创建用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Integer id, @RequestBody UserDTO dto) {

        dto.setRole(ROLE);
        if (!userService.validate(id, dto.getType())) {
            return Mono.error(new IllegalArgumentException("type is illegal"));
        }
        dto.setPassword(SALT + encoder.encode(dto.getPassword()));
        UserDTO user = userService.createUser(dto);
        if (user != null) {
            userDetailsService.addUserDetail(User.builder().passwordEncoder(password -> password.substring(5))
                    .username(dto.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole().split(","))
                    .build());
        }
        return Mono.empty();
    }

    @ApiOperation("修改用户")
    @PutMapping("/{mId}")
    public Mono<?> modifyManager(@RequestBody UserDTO dto, @ApiParam("需要修改的用户的id") @PathVariable Integer mId, @ApiParam(value = "修改用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Integer id) {
        if (!userService.validateById(id, mId)) {
            return Mono.error(new IllegalArgumentException("no access"));
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
            return Mono.error(new IllegalArgumentException("no access"));
        }
        userService.freezeUser(mId);
        return Mono.empty();
    }


    @ApiOperation("删除管理员")
    @DeleteMapping
    public Mono<?> deleteUser(@ApiParam("id以逗号分隔") @RequestParam Integer[] ids, @ApiParam(value = "删除用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Integer id) {
        for (Integer mId : ids) {
            if (!userService.validateById(id, mId)) {
                return Mono.error(new IllegalArgumentException("no access"));
            }
        }
        userService.batchDeleteUser(ids);
        return Mono.empty();
    }

    @ApiOperation("修改用户权限")
    @PutMapping("/{mId}/freeze")
    public Mono<?> changeRole(@PathVariable Integer mId, @ApiParam(value = "修改用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") Integer id, @ApiParam("1 GUEST 2 NORMAL 4 ADMIN") Integer role) {
        if (!userService.validateById(id, mId)) {
            return Mono.error(new IllegalArgumentException("no access"));
        }
        userService.changeRole(mId, role);
        return Mono.empty();
    }

//    public Mono<?>

}
