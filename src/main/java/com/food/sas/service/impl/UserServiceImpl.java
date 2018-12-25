package com.food.sas.service.impl;

import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.entity.QUser;
import com.food.sas.data.entity.User;
import com.food.sas.data.repository.UserRepository;
import com.food.sas.mapper.UserMapper;
import com.food.sas.service.IUserService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:59 PM
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<UserDTO> getUserPage(UserDTO dto, Pageable pageable) {
        QUser user = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(dto.getName())) {
            builder.and(user.name.like(dto.getName()));
        }
        if (StringUtils.isNotEmpty(dto.getPhone())) {
            builder.and(user.name.like(dto.getPhone()));
        }
        if (dto.getType() != null) {
            builder.and(user.type.eq(dto.getType()));
        }
        Page<User> userPage = userRepository.findAll(builder, pageable);
        return new PageImpl<>(Mappers.getMapper(UserMapper.class).fromEntitys(userPage.getContent()), pageable, userPage.getTotalElements());
    }
}
