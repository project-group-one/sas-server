package com.food.sas.security.service;

import com.food.sas.data.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

/**
 * @author Created by ygdxd_admin at 2019-01-05 3:04 PM
 */
@Slf4j
public class MyMapReactiveUserDetailsService
        implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private final Map<String, UserDetails> users;

    private final Map<String, UserDetails> freezedUser = new ConcurrentHashMap<>();

    /*
     * Creates a new instance using a {@link Map} that must be non blocking.
     * @param users a {@link Map} of users to use.
     */
    public MyMapReactiveUserDetailsService(Map<String, UserDetails> users) {
        this.users = users;
    }

    /* *
     * Creates a new instance
     * @param users the {@link UserDetails} to use
     */
    public MyMapReactiveUserDetailsService(UserDetails... users) {
        this(Arrays.asList(users));
    }

    /* *
     * Creates a new instance
     * @param users the {@link UserDetails} to use
     */
    public MyMapReactiveUserDetailsService(Collection<UserDetails> users) {
        Assert.notEmpty(users, "users cannot be null or empty");
        this.users = users.stream().collect(Collectors.toConcurrentMap(u -> getKey(u.getUsername()), Function.identity()));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        String key = getKey(username);
        UserDetails result = users.get(key);
        return result == null ? Mono.empty() : Mono.just(User.withUserDetails(result).build());
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return Mono.just(user)
                .map(u ->
                        User.withUserDetails(u)
                                .password(newPassword)
                                .build()
                )
                .doOnNext(u -> {
                    String key = getKey(user.getUsername());
                    this.users.put(key, u);
                });
    }

    private String getKey(String username) {
        return username.toLowerCase();
    }

    public void addUserDetail(UserDTO dto) {
//        users.put(new User.UserBuilder().username(dto.getUsername()).password(dto.getPassword()).passwordEncoder(s -> s).authorities(dto.getRole().split(",")).build());
        users.put(dto.getUsername(), new com.food.sas.security.User(dto.getUsername(), dto.getPassword(), AuthorityUtils.createAuthorityList(dto.getRole().split(","))));
    }

    public void addUserDetail(UserDetails userDetails){
        users.put(userDetails.getUsername(), userDetails);
    }

    public void freezeUser(String username){
        if (users.containsKey(username)){
            freezedUser.put(username, users.get(username));
            users.remove(username);
        }
    }
}
