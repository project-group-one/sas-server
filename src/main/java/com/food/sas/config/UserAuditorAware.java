package com.food.sas.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Created by ygdxd_admin at 2019-04-18 10:10 PM
 */
//@Configuration
public class UserAuditorAware implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {


        //

        return Optional.empty();
    }
}
