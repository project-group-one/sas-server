package com.food.sas.security;

import com.food.sas.data.entity.QUser;
import com.food.sas.data.repository.UserRepository;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.util.DigestUtils;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

import java.util.List;



/**
 * @author Created by ygdxd_admin at 2018-12-21 9:52 PM
 */
@EnableReactiveMethodSecurity
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Autowired
    private UserRepository repository;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyMapReactiveUserDetailsService userDetailsService() {
        List<UserDetails> userDetails = Lists.newArrayList();
        repository.findAll(QUser.user.type.lt(4)).forEach(u -> userDetails.add(User.builder().passwordEncoder(password -> password.substring(5))
                .username(u.getUsername())
                .password(u.getPassword())
                .authorities(u.getRole().split(","))
                .build()));
        System.out.println(userDetails);
        return new MyMapReactiveUserDetailsService(userDetails);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers( "/druid/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .httpBasic().and().formLogin();
        return http.build();
    }
}
