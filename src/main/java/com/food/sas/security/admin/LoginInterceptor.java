package com.food.sas.security.admin;

import com.food.sas.data.dto.AdministratorDTO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Created by ygdxd_admin at 2019-11-04 9:05 PM
 */
public class LoginInterceptor implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        exchange.getSession().subscribe(session -> {
//            session.getId()
//        }).

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();


        ServerWebExchange newWebExchange = exchange.mutate().request(request).response(response).build();

        return chain.filter(newWebExchange);
    }
}
