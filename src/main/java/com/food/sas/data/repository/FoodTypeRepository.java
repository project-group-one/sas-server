package com.food.sas.data.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.food.sas.data.entity.FoodType;
import org.springframework.stereotype.Repository;

/**
 * Created by zj on 2019/1/5
 */
@Repository
public interface FoodTypeRepository extends EntityGraphJpaRepository<FoodType, Long>, EntityGraphQuerydslPredicateExecutor<FoodType> {
}
