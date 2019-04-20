package com.food.sas.controller;

import com.food.sas.config.SmsSender;
import com.food.sas.data.dto.BaseResult;
import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.entity.VerificationCode;
import com.food.sas.data.repository.IVerificationCodeRepository;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
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

    @ApiOperation("登录后调用 获取必要参数")
    @GetMapping("/current")
    public Mono<?> getCurrentUser(Authentication authentication, ServerWebExchange exchange) {
        if (authentication != null && authentication.isAuthenticated()) {
            return Mono.just(userService.findUserByUsername(authentication.getName()));
        }
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return Mono.just("用户未登录");
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("用户列表查询 查询参数 username(半模糊) address type name(半模糊)")
    @GetMapping
    public BaseResult<List<UserDTO>> getUserPages(UserDTO dto, @RequestParam(value = "current", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<UserDTO> result = userService.getUserPage(dto, PageRequest.of(page - 1 < 0 ? 0 : page - 1, size));
        return new BaseResult<>(result.getContent(), result);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("添加用户")
    @PostMapping
    public Mono<?> registeredUser(@ApiParam(value = "创建用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id, @RequestBody UserDTO dto) {

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
    public Mono<?> modifyManager(@RequestBody UserDTO dto,
                                 @ApiParam("需要修改的用户的id") @PathVariable Long mId,
                                 @ApiParam(value = "修改用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id) {
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
    public Mono<?> freezeUser(@ApiParam(value = "冻结用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id,
                              @ApiParam("需要冻结的用户id") @PathVariable Long mId) {
        if (!userService.validateById(id, mId)) {
            return Mono.error(new IllegalArgumentException("no access"));
        }
        userService.freezeUser(mId);
        return Mono.empty();
    }


    @ApiOperation("删除管理员")
    @DeleteMapping
    public Mono<?> deleteUser(@ApiParam("id以逗号分隔") @RequestParam Long[] ids,
                              @ApiParam(value = "删除用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") @RequestParam Long id) {
        for (Long mId : ids) {
            if (!userService.validateById(id, mId)) {
                return Mono.error(new IllegalArgumentException("no access"));
            }
        }
        userService.batchDeleteUser(ids);
        return Mono.empty();
    }

    @ApiOperation("修改用户权限")
    @PutMapping("/{mId}/freeze")
    public Mono<?> changeRole(@PathVariable Long mId,
                              @ApiParam(value = "修改用户的用户id  如果该id的用户权限小于等于创建的用户 则失败") Long id,
                              @ApiParam("1 GUEST 2 NORMAL 4 ADMIN") Integer role) {
        if (!userService.validateById(id, mId)) {
            return Mono.error(new IllegalArgumentException("no access"));
        }
        userService.changeRole(mId, role);
        return Mono.empty();
    }

    @ApiOperation("手机注册")
    @PostMapping("/phone")
    public Mono<?> phoneRegister(@RequestParam String code, @RequestBody UserDTO userDTO) {

        if (StringUtils.isBlank(userDTO.getUsername()) || StringUtils.isBlank(userDTO.getPassword()) || userDTO.getPhone() == null) {
            return Mono.error(new RuntimeException("参数错误！"));
        }

        VerificationCode verificationCode = verificationCodeService.findVerificationCodeByPhone(userDTO.getPhone());
        if (verificationCode == null || !verificationCode.getPhone().equals(userDTO.getPhone()) || verificationCode.getRegistered() > 0 || verificationCode.getExpired().isBefore(LocalDateTime.now()) || !verificationCode.getCode().equals(code)) {
            return Mono.error(new RuntimeException("注册失败！"));
        }
        userDTO.setStatus(0);
        userDTO.setRole("CLIENT");
        userDTO.setId(null);
        userDTO.setType(0);
        userDTO.setPassword(SALT + encoder.encode(userDTO.getPassword()));
        userService.createUser(userDTO);
        verificationCode.setRegistered(1);
        verificationCodeService.saveOrUpdateVerificationCode(verificationCode);

        userDetailsService.addUserDetail(userDTO);
        return Mono.empty();
    }


    @ApiOperation("发送验证码")
    @GetMapping("/code")
    public Mono<?> sendSMS(@RequestParam String phone) {
        if (phone.length() != 11 || !phone.matches(PHONE_REGEX)) {
            return Mono.error(new IllegalArgumentException("手机号格式错误"));
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
            return Mono.error(new RuntimeException("短信发送失败！"));
        }


        return Mono.empty();
    }

    @ApiOperation("修改密码")
    @PutMapping("/pwd")
    public Mono<?> modifyPassword(Authentication authentication, @RequestParam String oPwd, @RequestParam String nPwd) {
        if (authentication.isAuthenticated()) {
            //判断旧密码是否相同
            UserDTO user = userService.findUserByUsername(authentication.getName());
            boolean b = encoder.matches(oPwd, user.getPassword().substring(5));
            if (b) {
                user.setPassword(SALT + encoder.encode(nPwd));
                userService.createUser(user);
                return userDetailsService.updatePassword(new com.food.sas.security.User(user.getId(), user.getType(), user.getUsername(), user.getPassword().substring(5), AuthorityUtils.createAuthorityList(user.getRole().split(","))), user.getPassword().substring(5));
            }
        }

        return Mono.error(new RuntimeException("修改密码失败"));
    }

}
