package com.food.sas.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;

/**
 * @author zj
 */
@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore(ErrorWebFluxAutoConfiguration.class)
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ErrorAutoConfiguration {

    private final ServerProperties serverProperties;
    private final ApplicationContext applicationContext;
    private final ResourceProperties resourceProperties;
    private final ServerCodecConfigurer serverCodecConfigurer;

    @Bean
    @Order(-1)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
        ErrorWebExceptionHandler exceptionHandler = new ErrorWebExceptionHandler(
                errorAttributes, this.resourceProperties, this.serverProperties.getError(), this.applicationContext);
        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }

}