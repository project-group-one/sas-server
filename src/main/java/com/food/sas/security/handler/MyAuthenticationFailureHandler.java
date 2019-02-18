package com.food.sas.security.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @author Created by ygdxd_admin at 2019-02-18 8:37 PM
 */
public class MyAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private final HttpStatus httpStatus;

    /**
     * Creates an instance with the provided status
     *
     * @param httpStatus the status to use
     */
    public MyAuthenticationFailureHandler(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }


    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()))
                .flatMap(response -> {
                    response.setStatusCode(this.httpStatus);
                    response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    DataBuffer buffer = dataBufferFactory.wrap(exception.getMessage().getBytes(
                            Charset.defaultCharset()));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                });
    }
}
