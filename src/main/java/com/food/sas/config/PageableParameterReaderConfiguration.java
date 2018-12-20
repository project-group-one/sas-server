package com.food.sas.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.schema.TypeNameExtractor;

/**
 * Created by zj on 2018/12/20
 */
@Configuration
@ConditionalOnClass(Pageable.class)
public class PageableParameterReaderConfiguration {

    @Bean
    public OperationPageableParameterReader pageableParameterReader(TypeNameExtractor nameExtractor, TypeResolver resolver) {
        return new OperationPageableParameterReader(nameExtractor, resolver);
    }

}
