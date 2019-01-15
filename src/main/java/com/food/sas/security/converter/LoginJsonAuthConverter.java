package com.food.sas.security.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.sas.data.dto.UsernamePasswordRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author Created by ygdxd_admin at 2019-01-15 9:31 PM
 */
@Component
public class LoginJsonAuthConverter implements Function<ServerWebExchange, Mono<Authentication>> {

    private final ObjectMapper mapper;

    public LoginJsonAuthConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Mono<Authentication> apply(ServerWebExchange exchange) {
        return exchange.getRequest().getBody()
                .next()
                .flatMap(buffer -> {
                    try {
                        UsernamePasswordRequest request = mapper.readValue(buffer.asInputStream(), UsernamePasswordRequest.class);
                        return Mono.just(request);
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                })
                .map(request -> new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    }
}
