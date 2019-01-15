package com.food.sas.security.endpoint;

import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.dto.UsernamePasswordRequest;
import com.food.sas.data.entity.User;
import com.food.sas.security.SecurityConfiguration;
import com.food.sas.security.jwt.JwtBody;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
import com.food.sas.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
    public Mono<?> getToken(@RequestBody UsernamePasswordRequest body, ServerWebExchange exchange) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(body.getUsername(), body.getPassword());
        final Mono<Authentication> authentication = reactiveAuthenticationManager.authenticate(token);
        SecurityContextImpl securityContext = new SecurityContextImpl();
        authentication.subscribe(authentication1 -> {
            securityContext.setAuthentication(authentication1);
        });
        repository.save(exchange, securityContext).then(Mono.empty())
                .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
                .subscribe();


        return userDetailsService.findByUsername(body.getUsername()).map(userDetails -> {
            UserDTO userDTO = userService.findUserByUsername(body.getUsername());
            return JwtBody.newBuiler()
                    .setAccessToken(JwtHelper.encode(SecurityConfiguration.KEY, SecurityConfiguration.HMAC).getEncoded())
                    .setExpired(3600L)
                    .setRefreshToken(null)
                    .setUserId(userDTO.getId())
                    .setUserType(userDTO.getType()).builde();
        });
//        return Mono.just(result);
    }


}
