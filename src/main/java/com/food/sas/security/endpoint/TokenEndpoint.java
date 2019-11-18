package com.food.sas.security.endpoint;

import com.alibaba.fastjson.JSON;
import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.dto.UsernamePasswordRequest;
import com.food.sas.security.SecurityConfiguration;
import com.food.sas.security.jwt.JwtBody;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author Created by ygdxd_admin at 2019-01-14 10:03 PM
 */
@Api(tags = "token 获取")
@RequestMapping("/auth")
@RestController
public class TokenEndpoint {

    @Autowired
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Autowired
    private MyMapReactiveUserDetailsService userDetailsService;

    @Autowired
    private WebSessionServerSecurityContextRepository repository;

    @Autowired
    private IUserService userService;


    @ApiOperation("获取密钥 其实还是session登录 现在密钥还没卵用， 密码错误会报错")
    @PostMapping("/token")
    public Mono<?> getToken(@RequestBody UsernamePasswordRequest body, @ApiIgnore ServerWebExchange exchange) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword(), AuthorityUtils.createAuthorityList("ADMIN"));
        Mono<Authentication> authentication = reactiveAuthenticationManager.authenticate(token);
//        SecurityContextImpl securityContext = new SecurityContextImpl();
//        authentication.subscribe(securityContext::setAuthentication);
//
//        if (securityContext.getAuthentication() == null) {
//            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
//            return Mono.just("用户名或者密码错误");
//        }
//        repository.save(exchange, securityContext).then(Mono.empty())
//                .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
//                .subscribe();


        return authentication.flatMap(authentication1 -> {
            SecurityContextImpl securityContext = new SecurityContextImpl();
            securityContext.setAuthentication(authentication1);
            if (securityContext.getAuthentication() == null) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return Mono.just("用户名或者密码错误");
            }
            repository.save(exchange, securityContext).then(Mono.empty())
                    .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
                    .subscribe();
            return userDetailsService.findByUsername(body.getUsername()).map(userDetails -> {
                System.out.println(JSON.toJSONString(userDetails));
                UserDTO userDTO = userService.findUserByUsername(body.getUsername());
                return JwtBody.newBuiler()
                        .setAccessToken(JwtHelper.encode(JSON.toJSONString(userDetails), SecurityConfiguration.HMAC).getEncoded())
                        .setExpired(3600L + LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli())
                        .setRefreshToken(null)
                        .setUserId(userDTO.getId())
                        .setUserType(userDTO.getType()).builde();
            });
        }).onErrorReturn("账号或密码错误");
//        userDetailsService.findByUsername(body.getUsername()).map(userDetails -> {
//            System.out.println(JSON.toJSONString(userDetails));
//            UserDTO userDTO = userService.findUserByUsername(body.getUsername());
//            return JwtBody.newBuiler()
//                    .setAccessToken(JwtHelper.encode(JSON.toJSONString(userDetails), SecurityConfiguration.HMAC).getEncoded())
//                    .setExpired(3600L)
//                    .setRefreshToken(null)
//                    .setUserId(userDTO.getId())
//                    .setUserType(userDTO.getType()).builde();
//        });
//        return Mono.just(result);
    }

    @ApiOperation("获取密钥 其实还是session登录 现在密钥还没卵用， 密码错误会报错")
    @PostMapping("/token/refresh")
    public Mono<?> refreshToken() {
        return Mono.empty();
    }


}
