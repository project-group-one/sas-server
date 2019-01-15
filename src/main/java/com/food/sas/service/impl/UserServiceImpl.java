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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:59 PM
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final int FREEZED_STATUS = 1;

    private static final String ROLE_ADMIN = "ADMIN";
    private static final int ROLE_ADMIN_I = 4;
    private static final String ROLE_NORMAL = "NORMAL";
    private static final int ROLE_NORMAL_I = 2;
    private static final String ROLE_GUEST = "GUEST";
    private static final int ROLE_GUEST_I = 1;
    private static final String ROLE_ALL = "ALL";

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

    @Override
    public UserDTO findUserByUsername(String username) {
        QUser qUser = QUser.user;
        return Mappers.getMapper(UserMapper.class).fromEntity(userRepository.findOne(qUser.username.eq(username)).get());

    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        dto.setType(0);
        return mapper.fromEntity(userRepository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public void modifyManager(UserDTO dto, Integer id) {
        userRepository.findById(id).ifPresent(user -> {
            if (StringUtils.isNotEmpty(dto.getName())) {
                user.setName(dto.getName());
            }
            if (StringUtils.isNotEmpty(dto.getPhone())) {
                user.setPhone(dto.getPhone());
            }
            if (StringUtils.isNotEmpty(dto.getAddress())) {
                user.setAddress(dto.getAddress());
            }
            if (dto.getType() != null) {
                user.setType(dto.getType());
            }
            userRepository.saveAndFlush(user);

        });
    }

    @Override
    public boolean validate(Integer id, Integer type) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent() && optional.get().getType() > type) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateById(Integer id, Integer mId) {
        //操作用户
        Optional<User> user1 = userRepository.findById(id);
        //被更改的用户
        Optional<User> user2 = userRepository.findById(mId);

        if (user1.isPresent() && user2.isPresent() && user1.get().getType() > user2.get().getType()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void batchDeleteUser(Integer[] ids) {
        if (ids != null && ids.length > 1) {
            List<User> list = new ArrayList<>(ids.length);
            for (Integer id : ids) {
                User user = new User();
                user.setId(id);
                list.add(user);
            }
            userRepository.deleteAll(list);
        }
    }

    @Override
    public void freezeUser(Integer id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setStatus(FREEZED_STATUS);
            userRepository.saveAndFlush(user);
        });
    }

    @Override
    public void changeRole(Integer mId, Integer role) {
        userRepository.findById(mId).ifPresent(user -> {
            user.setRole(getRole(role));
            userRepository.saveAndFlush(user);
        });
    }

    private String getRole(int role) {
        return (role & ROLE_ADMIN_I) == ROLE_ADMIN_I ? ROLE_ADMIN : (role & ROLE_NORMAL_I) == ROLE_NORMAL_I ? ROLE_NORMAL : ROLE_GUEST;
    }
}
