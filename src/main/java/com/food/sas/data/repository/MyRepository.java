package com.food.sas.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Created by ygdxd_admin at 2018-12-21 10:55 PM
 */
@NoRepositoryBean
public interface MyRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {
}
