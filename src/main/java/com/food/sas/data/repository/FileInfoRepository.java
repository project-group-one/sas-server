package com.food.sas.data.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import com.food.sas.data.entity.FileInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zj on 2018/12/21
 */
@Repository
public interface FileInfoRepository extends EntityGraphJpaRepository<FileInfo, Long>, EntityGraphQuerydslPredicateExecutor<FileInfo> {

    FileInfo findByPath(String path);
}
