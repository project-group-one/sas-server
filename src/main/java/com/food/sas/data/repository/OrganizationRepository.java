package com.food.sas.data.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.food.sas.data.entity.ExaminingReport;
import com.food.sas.data.entity.Organization;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:26 PM
 */
public interface OrganizationRepository extends EntityGraphJpaRepository<Organization, Long>, EntityGraphQuerydslPredicateExecutor<Organization> {

    boolean existsByName(String name);
}
