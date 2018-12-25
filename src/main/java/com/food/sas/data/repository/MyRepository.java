package com.food.sas.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Created by ygdxd_admin at 2018-12-21 10:55 PM
 */
@NoRepositoryBean
public interface MyRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {


}
