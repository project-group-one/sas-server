package com.food.sas.controller;

import com.alibaba.fastjson.JSON;
import com.food.sas.data.dto.AdministratorDTO;
import com.food.sas.data.dto.AdministratorRequest;
import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.response.SimpleResponse;
import com.food.sas.service.IAdministratorService;
import com.food.sas.service.IUserService;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.nio.charset.Charset;

/**
 * @author Created by ygdxd_admin at 2019-11-04 9:13 PM
 */
@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private LoadingCache<String, AdministratorDTO> cache;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAdministratorService administratorService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public Mono<?> login(@Valid @RequestBody AdministratorRequest body, ServerWebExchange exchange) {
//        cache.put();
        AdministratorDTO administrator = administratorService.getAdministrator(body.getUsername(), encoder.encode(body.getPassword()));
        if (administrator == null) {
            return Mono.defer(() -> Mono.just(exchange.getResponse()))
                    .flatMap(response -> {
                        response.setStatusCode(HttpStatus.FORBIDDEN);
                        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
                        DataBufferFactory dataBufferFactory = response.bufferFactory();
                        DataBuffer buffer = dataBufferFactory.wrap(JSON.toJSONString(SimpleResponse.forbidden()).getBytes(
                                Charset.defaultCharset()));
                        return response.writeWith(Mono.just(buffer))
                                .doOnError(error -> DataBufferUtils.release(buffer));
                    });
        }

        return Mono.just(",");
    }

    @PostMapping("/register")
    public Mono<?> register(@Valid @RequestBody AdministratorRequest body) {
        return Mono.just("a");
    }

    @GetMapping("/searchCustom")
    public Mono<?> searchUserList(UserDTO request, @PageableDefault Pageable pageable) {
        userService.getUserPage(request, pageable);
        return Mono.just(null);
    }


}
