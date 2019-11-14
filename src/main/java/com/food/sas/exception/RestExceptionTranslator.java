package com.food.sas.exception;

import com.food.sas.data.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author zj
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class RestExceptionTranslator {

    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Result> handleError(ServerWebInputException e) {
        log.warn("缺少请求参数:{}", e.getMessage());
        MethodParameter parameter = e.getMethodParameter();
        String message = String.format("缺少必要的请求参数: %s", parameter.getParameterName());
        return Mono.just(Result.ofFail(400, message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Result> handleError(MethodArgumentNotValidException e) {
        log.warn("参数验证失败:{}", e.getMessage());
        return Mono.just(handleError(e.getBindingResult()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Result> handleError(WebExchangeBindException e) {
        log.warn("参数绑定失败:{}", e.getMessage());
        return Mono.just(handleError(e.getBindingResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Result> handleError(ConstraintViolationException e) {
        log.warn("参数验证失败:{}", e.getMessage());
        return Mono.just(handleError(e.getConstraintViolations()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<Result>> handleError(ResponseStatusException e) {
        log.error("响应状态异常:{}", e.getMessage());
        ResponseEntity<Result> entity = ResponseEntity.status(e.getStatus())
                .body(Result.ofFail(500, e.getMessage()));
        return Mono.just(entity);
    }

    Result handleError(BindingResult result) {
        FieldError error = result.getFieldError();
        String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return Result.ofFail(500, message);
    }

    Result handleError(Set<ConstraintViolation<?>> violations) {
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        return Result.ofFail(400, message);
    }
}