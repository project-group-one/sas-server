package com.food.sas.controller;

import com.food.sas.config.SmsSender;
import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.entity.VerificationCode;
import com.food.sas.data.response.Result;
import com.food.sas.exception.BadException;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
import com.food.sas.service.IUserService;
import com.food.sas.service.IVerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:15 PM
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
public class UserController {

    private static final String SALT = "$qiqi";

    private static final String ROLE = "NORMAL";

    private static final String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])|(18[0-9])|(19[8,9]))\\d{8}$";


    private static final String ROLE_ADMIN = "ADMIN";
    private static final int ROLE_ADMIN_I = 4;
    private static final String ROLE_NORMAL = "CLIENT";
    private static final int ROLE_NORMAL_I = 2;
    private static final String ROLE_GUEST = "GUEST";
    private static final int ROLE_GUEST_I = 1;
    private static final String ROLE_ALL = "ALL";

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVerificationCodeService verificationCodeService;

    @Autowired
    private MyMapReactiveUserDetailsService userDetailsService;

    @Autowired
    private SmsSender sender;


    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public Mono<Result<UserDTO>> searchUser(@PathVariable Long id, @ApiIgnore Authentication authentication, @ApiIgnore ServerWebExchange exchange) {
        if (authentication == null || (!authentication.isAuthenticated())) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return Mono.just(Result.fail("用户未登录"));
        }
        return Mono.just(Result.success(userService.searchUserById(id)));
    }

    @ApiOperation("登录后调用 获取必要参数")
    @GetMapping("/current")
    public Mono<Result<UserDTO>> getCurrentUser(Authentication authentication, ServerWebExchange exchange) {
        if (authentication != null && authentication.isAuthenticated()) {
            return Mono.just(Result.success(userService.findUserByUsername(authentication.getName())));
        }
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.just(Result.fail("用户未登录"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("用户列表查询 查询参数 username(半模糊) address type name(半模糊)")
    @GetMapping
    public Mono<Result<List<UserDTO>>> getUserPages(UserDTO dto, @RequestParam(value = "current", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<UserDTO> result = userService.getUserPage(dto, PageRequest.of(Math.max(page - 1, 0), size));
        return Mono.just(Result.success(result.getContent(), result));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("添加用户")
    @PostMapping
    public Mono<Void> registeredUser(@ApiParam(value = "创建用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id, @RequestBody UserDTO dto) {

        dto.setRole(ROLE);
        if (!userService.validate(id, dto.getType())) {
            throw new BadException("type is illegal");
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

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("修改用户")
    @PutMapping("/{mId}")
    public Mono<Void> modifyManager(@RequestBody UserDTO dto,
                                    @ApiParam("需要修改的用户的id") @PathVariable Long mId,
                                    @ApiParam(value = "修改用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id) {
        if (!userService.validateById(id, mId)) {
            throw new BadException("no access");
        }
        if (dto.getType() != null && !userService.validate(id, dto.getType())) {
            throw new BadException("type is illegal");
        }
        userService.modifyManager(dto, mId);
        return Mono.empty();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("冻结用户")
    @PostMapping("/{mId}/freeze")
    public Mono<Void> freezeUser(@ApiParam(value = "冻结用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id,
                                 @ApiParam("需要冻结的用户id") @PathVariable Long mId) {
        if (!userService.validateById(id, mId)) {
            throw new BadException("no access");
        }
        userService.freezeUser(mId);
        userDetailsService.freezeUser(userService.searchUserById(mId).getUsername());
        return Mono.empty();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("解冻用户")
    @PostMapping("/{mId}/thaw")
    public Mono<?> thawUser(@ApiParam("需要解冻的用户id") @PathVariable Long mId) {
        userService.thawUser(mId);
        return Mono.empty();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("删除管理员")
    @DeleteMapping
    public Mono<Void> deleteUser(@ApiParam("id以逗号分隔") @RequestParam Long[] ids,
                                 @ApiParam(value = "删除用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id) {
        for (Long mId : ids) {
            if (!userService.validateById(id, mId)) {
                throw new BadException("no access");
            }
        }
        userService.batchDeleteUser(ids);
        return Mono.empty();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("修改用户权限")
    @PutMapping("/{mId}/freeze")
    public Mono<Void> changeRole(@PathVariable Long mId,
                                 @ApiParam(value = "修改用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") Long id,
                                 @ApiParam("1 GUEST 2 NORMAL 4 ADMIN") Integer role) {
        if (!userService.validateById(id, mId)) {
            throw new BadException("no access");
        }
        String r = getRole(role);
        String s = userService.changeRole(mId, r);
        if (s != null) {
//            userDetailsService.changeRole(s, r);
        }
        return Mono.empty();
    }

    @ApiOperation("手机注册")
    @PostMapping("/phone")
    public Mono<Void> phoneRegister(@RequestParam String code, @RequestBody UserDTO userDTO) {

        if (StringUtils.isBlank(userDTO.getUsername()) || StringUtils.isBlank(userDTO.getPassword()) || userDTO.getPhone() == null) {
            throw new BadException("参数错误");
        }

        if (null != userService.findUserByUsername(userDTO.getUsername())) {
            return Mono.error(new RuntimeException("用户名已被注册！"));
        }

        VerificationCode verificationCode = verificationCodeService.findVerificationCodeByPhone(userDTO.getPhone());
        if (verificationCode == null || !verificationCode.getPhone().equals(userDTO.getPhone()) || verificationCode.getRegistered() > 0 || verificationCode.getExpired().isBefore(LocalDateTime.now()) || !verificationCode.getCode().equals(code)) {
            throw new BadException("注册失败");
        }
        userDTO.setStatus(0);
        userDTO.setRole("CLIENT");
        userDTO.setId(null);
        userDTO.setType(0);
        userDTO.setPassword(SALT + encoder.encode(userDTO.getPassword()));
        userService.createUser(userDTO);
        verificationCode.setRegistered(1);
        verificationCodeService.saveOrUpdateVerificationCode(verificationCode);

        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        userDetailsService.addUserDetail(userDTO);
        return Mono.empty();
    }


    @ApiOperation("发送验证码")
    @GetMapping("/code")
    public Mono<Void> sendSMS(@RequestParam String phone) {
        if (phone.length() != 11 || !phone.matches(PHONE_REGEX)) {
            throw new BadException("手机号格式错误");
        }
        int i = 1000000 + ThreadLocalRandom.current().nextInt(999999);
        String code = String.valueOf(i).substring(1);
        if (sender.send(phone, code)) {

            VerificationCode verificationCode = null;
            if ((verificationCode = verificationCodeService.findVerificationCodeByPhone(phone)) == null) {
                verificationCode = new VerificationCode();
            }
            verificationCode.setCode(code);
            verificationCode.setExpired(LocalDateTime.now().plusMinutes(15L));
            verificationCode.setPhone(phone);
            verificationCode.setRegistered(0);
            verificationCodeService.saveOrUpdateVerificationCode(verificationCode);
        } else {
            throw new BadException("短信发送失败");
        }
        return Mono.empty();
    }

    @ApiOperation("修改密码")
    @PutMapping("/pwd")
    public Result<Mono<UserDetails>> modifyPassword(Authentication authentication, @RequestParam String oPwd, @RequestParam String nPwd) {
        if (authentication.isAuthenticated()) {
            //判断旧密码是否相同
            UserDTO user = userService.findUserByUsername(authentication.getName());
            boolean b = encoder.matches(oPwd, user.getPassword().substring(5));
            if (b) {
                user.setPassword(SALT + encoder.encode(nPwd));
                userService.createUser(user);
                Result.success(userDetailsService.updatePassword(new com.food.sas.security.User(user.getId(), user.getType(), user.getUsername(), user.getPassword().substring(5), AuthorityUtils.createAuthorityList(user.getRole().split(","))), user.getPassword().substring(5)));
            }
        }
        return Result.fail("修改密码失败");
    }

    @ApiOperation("获取没有组织的用户")
    @GetMapping("/not-org")
    public Mono<Result<List<UserDTO>>> getUsersHasNotOrg() {
        List<UserDTO> users = userService.getUsersHasNoOrg();
        return Mono.just(Result.success(users));
    }


    private String getRole(int role) {
        StringBuilder builder = new StringBuilder();
        if ((role & ROLE_ADMIN_I) == ROLE_ADMIN_I) {
            builder.append(",").append(ROLE_ADMIN);
        } else {
            if ((role & ROLE_NORMAL_I) == ROLE_NORMAL_I) {
                builder.append(",").append(ROLE_NORMAL);
            } else {
                builder.append(",").append(ROLE_GUEST);
            }
        }
        return builder.substring(1);
    }

}
