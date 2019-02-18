package com.food.sas.security;

import com.food.sas.data.entity.QUser;
import com.food.sas.data.repository.UserRepository;
import com.food.sas.security.filter.MyTokenFilter;
import com.food.sas.security.handler.MyAuthenticationFailureHandler;
import com.food.sas.security.handler.MyRestfulSuccessHandler;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.DelegatingServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

import java.util.List;



/**
 * @author Created by ygdxd_admin at 2018-12-21 9:52 PM
 */
@EnableReactiveMethodSecurity
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    public static final String KEY = "qiqi";

    public static final MacSigner HMAC = new MacSigner(KEY);

    public static final String AUTH_HEADER = "Authorization";

    public static final String TOKEN_HEAD = "Bearer ";

    //开始
    private static final String S1 = "#";

    //分隔
    private static final String S2 = ":";

    @Autowired
    private UserRepository repository;

    @Bean
    public WebSessionServerSecurityContextRepository webSessionServerSecurityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

    private MyAuthenticationFailureHandler authenticationFailureHandler() {
        return new MyAuthenticationFailureHandler(HttpStatus.FORBIDDEN);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(@Qualifier("userDetailsService") MyMapReactiveUserDetailsService userDetailsServic, BCryptPasswordEncoder encoder) {
        ReactiveAuthenticationManager reactiveAuthenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsServic);
        ((UserDetailsRepositoryReactiveAuthenticationManager) reactiveAuthenticationManager).setPasswordEncoder(encoder);
        return reactiveAuthenticationManager;
    }

    @Bean
    public MyMapReactiveUserDetailsService userDetailsService() {
        List<UserDetails> userDetails = Lists.newArrayList();
        repository.findAll(QUser.user.type.lt(4)).forEach(u -> userDetails.add(
                new com.food.sas.security.User(u.getId(), u.getType(), u.getUsername(), u.getPassword().substring(5), AuthorityUtils.createAuthorityList(u.getRole().split(",")))
//                User.builder().passwordEncoder(password -> password.substring(5))
//                .username(u.getUsername())
//                .password(u.getPassword())
//                .authorities(new StringBuffer(u.getRole()).append(",").append(S1).append(u.getId()).append(S2).append(u.getType()).toString().split(","))
////                .roles(u.getRole().split(","))
//                .credentialsExpired(true)
//                .build()
        ));
        return new MyMapReactiveUserDetailsService(userDetails);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveAuthenticationManager reactiveAuthenticationManager, WebSessionServerSecurityContextRepository repository, MyMapReactiveUserDetailsService userDetailsService) {
        http
                .addFilterAt(new MyTokenFilter(KEY, HMAC, userDetailsService, repository), SecurityWebFiltersOrder.FIRST)
                .authenticationManager(reactiveAuthenticationManager)
                .authorizeExchange()
                .pathMatchers("/auth/**").permitAll()
//                .pathMatchers( "/druid/*").permitAll()
                .pathMatchers("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().disable().securityContextRepository(repository);
        MyRestfulSuccessHandler handler = new MyRestfulSuccessHandler();
        http.formLogin().authenticationSuccessHandler(new DelegatingServerAuthenticationSuccessHandler(handler)).loginPage("/login").authenticationFailureHandler(authenticationFailureHandler()).and().logout().logoutUrl("/signout");
        return http.build();
    }


}
