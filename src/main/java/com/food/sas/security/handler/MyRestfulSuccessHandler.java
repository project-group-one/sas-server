package com.food.sas.security.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by ygdxd_admin at 2019-01-13 1:32 PM
 */
public class MyRestfulSuccessHandler implements ServerAuthenticationSuccessHandler {

    //开始
    private static final String S1 = "#";

    //分隔
    private static final String S2 = ":";


    private URI location = URI.create("/");

    private static final String KEY = "qiqi";

    private static final MacSigner HMAC = new MacSigner(KEY);
//    static final MacSigner hmac = new MacSigner(JwtSpecData.HMAC_KEY);

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        User user = null;
        if (authentication.getPrincipal() != null) {
            ServerWebExchange exchange = webFilterExchange.getExchange();
            ServerHttpResponse response = exchange.getResponse();
            user = (User) authentication.getPrincipal();
            Map<String, String> userInfo = new HashMap<>(4);
            userInfo.put("username", user.getUsername());
            userInfo.put("role", JSON.toJSONString(user.getAuthorities().stream().filter(s -> !s.getAuthority().startsWith(S1)).collect(Collectors.toList())));
            user.getAuthorities().stream().filter(authority ->
                    authority.getAuthority().contains(":") && authority.getAuthority().startsWith("#") && authority.getAuthority().split(S2).length == 2)
                    .forEach(authority -> {
                        String[] strs = authority.getAuthority().substring(1).split(S2);
                        userInfo.put("id", String.valueOf(strs[0]));
                        userInfo.put("type", String.valueOf(strs[1]));
                    });
            System.out.println(JSON.toJSONString(userInfo));
            response.addCookie(ResponseCookie.from("token", JwtHelper.encode(JSON.toJSONString(userInfo), HMAC).getEncoded()).maxAge(5 * 60 * 1000).build());
            response.getHeaders().add("alg", HMAC.algorithm());
        }
        return Mono.empty();
    }
}
