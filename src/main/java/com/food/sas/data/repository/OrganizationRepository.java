package com.food.sas.data.repository;

import com.food.sas.data.entity.Organization;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author Created by ygdxd_admin at 2019-01-06 8:26 PM
 */
public interface OrganizationRepository extends MyRepository<Organization, Integer>, QuerydslPredicateExecutor<Organization> {
}
