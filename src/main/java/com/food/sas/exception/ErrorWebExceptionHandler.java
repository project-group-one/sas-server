package com.food.sas.exception;

import com.food.sas.data.response.Result;
import com.food.sas.data.response.SystemCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author zj
 */
@Slf4j
@Component
@Order(-2)
public class ErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    public ErrorWebExceptionHandler(ErrorAttributes attributes, ResourceProperties properties,
                                    ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(attributes, properties, errorProperties, applicationContext);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = this.getError(request);
        HttpStatus status = determineHttpStatus(error);
        // 拼接地址
        MultiValueMap<String, String> queryParams = request.queryParams();
        String requestUrl = UriComponentsBuilder.fromPath(request.path()).queryParams(queryParams).build().toUriString();
        log.error(String.format("URL:%s error status:%d", requestUrl, status.value()), error);
        // 返回消息
        String message = status.value() + ":" + status.getReasonPhrase();
        Result result;
        if (error instanceof BadException) {
            result = ((BadException) error).getResult();
            result = Optional.ofNullable(result).orElse(Result.fail(SystemCode.FAILURE));
        } else {
            result = Result.fail(SystemCode.FAILURE, message);
        }
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result));
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        if (error instanceof ResponseStatusException) {
            return ((ResponseStatusException) error).getStatus();
        } else {
            ResponseStatus responseStatus = AnnotatedElementUtils.findMergedAnnotation(error.getClass(), ResponseStatus.class);
            return responseStatus != null ? responseStatus.code() : HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}