package com.food.sas.security.filter;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.macs.HMac;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.jwt.crypto.sign.SignerVerifier;
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

    public MyTokenFilter(String key, SignerVerifier signer) {
        this.key = key;
        this.signer = signer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

//        if (exchange.getRequest().getHeaders().containsKey(AUTH_HEADER)){
//            String allToken = exchange.getRequest().getHeaders().getFirst("AUTH_HEADER");
//            if (StringUtils.isNoneEmpty(allToken) && allToken.startsWith(TOKEN_HEAD) && allToken.length() > TOKEN_HEAD.length()){
//                String token = allToken.substring(TOKEN_HEAD.length());
//                JwtHelper.decodeAndVerify(token, signer).getEncoded();
//            }
//
//        }

        return chain.filter(exchange);
    }
}
