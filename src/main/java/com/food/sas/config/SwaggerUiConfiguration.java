package com.food.sas.config;

import com.fasterxml.classmate.TypeResolver;
import com.food.sas.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Created by zj on 2018/12/20
 */
@Configuration
@EnableSwagger2WebFlux
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerUiConfiguration {

    private final TypeResolver typeResolver;
    private final SwaggerProperties swaggerProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    public SwaggerUiConfiguration(TypeResolver typeResolver, SwaggerProperties swaggerProperties) {
        this.typeResolver = typeResolver;
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket swaggerUiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(StringUtils.isEmpty(swaggerProperties.getBasePackage())
                        ? RequestHandlerSelectors.any()
                        : RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .groupName(StringUtils.isEmpty(swaggerProperties.getGroupName())
                        ? "default"
                        : swaggerProperties.getGroupName())
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(
                                typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)
                        ),
                        newRule(
                                typeResolver.resolve(Collection.class, WildcardType.class),
                                typeResolver.resolve(List.class, WildcardType.class)
                        )
                )
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false);
    }

    @Bean
    UiConfiguration swaggerUiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .validatorUrl(null)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName)
                .description("接口遵循RESTFul规则,可参考:https://github.com/aisuhua/device-api")
                .version("X.0.0")
                .build();
    }

}
