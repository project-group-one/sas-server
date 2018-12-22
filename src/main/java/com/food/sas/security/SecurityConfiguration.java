package com.food.sas.security;

/*import com.food.sas.data.entity.QUser;
import com.food.sas.data.repository.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

*/

import com.food.sas.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Created by ygdxd_admin at 2018-12-21 9:52 PM
 *//*
@EnableReactiveMethodSecurity
@Configuration
@EnableWebFluxSecurity*/
public class SecurityConfiguration {

    @Autowired
    private UserRepository repository;

    /*@Bean
    public MapReactiveUserDetailsService userDetailsService() {
        List<UserDetails> userDetails = Lists.newArrayList();
        repository.findAll(QUser.user.type.eq(0)).forEach(u -> userDetails.add(User.withDefaultPasswordEncoder()
                .username(u.getUsername())
                // TODO change to encrypt
                .password(u.getPassword())
                .roles(u.getRole())
                .build()));
        return new MapReactiveUserDetailsService(userDetails);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .httpBasic().and()
                .formLogin();
        return http.build();
    }*/
}
