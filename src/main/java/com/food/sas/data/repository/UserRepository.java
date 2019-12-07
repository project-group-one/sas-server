package com.food.sas.data.repository;

import com.food.sas.data.entity.User;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-21 11:05 PM
 */
public interface UserRepository extends MyRepository<User, Long> {

    List<User> findByIdIn(List<Long> ids);

    void deleteByIdIn(List<Long> ids);
}
