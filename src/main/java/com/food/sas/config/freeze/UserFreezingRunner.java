package com.food.sas.config.freeze;

import com.food.sas.enums.UserStatusEnum;
import com.food.sas.security.service.MyMapReactiveUserDetailsService;
import com.food.sas.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Created by ygdxd_admin at 2019-12-06 9:36 PM
 */
@Component
public class UserFreezingRunner implements CommandLineRunner {

    @Autowired
    private IUserService userService;

    @Autowired
    private MyMapReactiveUserDetailsService userDetailsService;

    @Override
    public void run(String... args) throws Exception {
        userService.getUserIdsByStatus(UserStatusEnum.FREEZING.getCode()).forEach(username -> userDetailsService.freezeUser(username));


    }
}
