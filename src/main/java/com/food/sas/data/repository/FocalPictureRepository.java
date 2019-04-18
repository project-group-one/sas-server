package com.food.sas.data.repository;

import com.food.sas.data.entity.FocalPicture;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author Created by ygdxd_admin at 2019-01-08 10:09 PM
 */
public interface FocalPictureRepository extends MyRepository<FocalPicture, Long>, QuerydslPredicateExecutor<FocalPicture> {
}
