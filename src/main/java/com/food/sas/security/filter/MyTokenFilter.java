package com.food.sas.security.filter;

import com.alibaba.fastjson.JSON;
import com.food.sas.exception.BadException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.SignerVerifier;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Created by ygdxd_admin at 2019-01-14 8:19 PM
 */
public class MyTokenFilter implements WebFilter {

    private static final String AUTH_HEADER = "Authorization";

    private static final String TOKEN_HEAD = "Bearer";

    private final String key;

    private final SignerVerifier signer;

    private final ReactiveUserDetailsService userDetailsService;

    private final WebSessionServerSecurityContextRepository repository;


    public MyTokenFilter(String key, SignerVerifier signer, ReactiveUserDetailsService userDetailsService, WebSessionServerSecurityContextRepository repository) {
        this.key = key;
        this.signer = signer;
        this.userDetailsService = userDetailsService;
        this.repository = repository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        if (exchange.getRequest().getHeaders().containsKey(AUTH_HEADER)) {
            String allToken = exchange.getRequest().getHeaders().getFirst(AUTH_HEADER);
            if (StringUtils.isNoneEmpty(allToken) && allToken.startsWith(TOKEN_HEAD) && allToken.length() > TOKEN_HEAD.length()) {
                String token = allToken.substring(TOKEN_HEAD.length() + 1);
                String json = JwtHelper.decode(token).getClaims();
                UserDetails userDetails = JSON.parseObject(json, User.class);

                final Mono<Authentication> authentication = userDetailsService.findByUsername(userDetails.getUsername()).filter(u -> u.getPassword().equals(userDetails.getPassword()))
                        .switchIfEmpty(Mono.defer(() -> Mono.error(new BadException("Invalid Credentials"))))
                        .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()));
//
//                SecurityContextImpl securityContext = new SecurityContextImpl();
//                authentication.subscribe(authentication1 -> {
//                    securityContext.setAuthentication(authentication1);
//                });
//                repository.save(exchange, securityContext).then(Mono.empty())
//                        .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
//                        .subscribe();

                return authentication.flatMap(authentication1 -> {
                    SecurityContextImpl securityContext = new SecurityContextImpl();
                    securityContext.setAuthentication(authentication1);
                    if (securityContext.getAuthentication() == null) {
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return chain.filter(exchange);
                    }
                    repository.save(exchange, securityContext).then(Mono.empty())
                            .subscriberContext(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)))
                            .subscribe();
                    return chain.filter(exchange);
                });
            }

        }

        return chain.filter(exchange);
    }
}
