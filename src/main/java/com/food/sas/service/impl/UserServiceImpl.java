package com.food.sas.service.impl;

import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.dto.UserVerificationDTO;
import com.food.sas.data.entity.QUser;
import com.food.sas.data.entity.QUserVerification;
import com.food.sas.data.entity.User;
import com.food.sas.data.entity.UserVerification;
import com.food.sas.data.repository.UserRepository;
import com.food.sas.data.repository.UserVerificationRepository;
import com.food.sas.mapper.UserMapper;
import com.food.sas.mapper.UserVerificationMapper;
import com.food.sas.service.IUserService;
import com.food.sas.util.BooleanBuilderHelper;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:59 PM
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final int FREEZED_STATUS = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserVerificationRepository userVerificationRepository;

    @Autowired
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<UserDTO> getUserPage(UserDTO dto, Pageable pageable) {
        QUser user = QUser.user;
        BooleanBuilder builder = BooleanBuilderHelper.newBuilder().andStringLike(user.name, dto.getName())
                .andStringLike(user.username, dto.getUsername())
                .andStringLike(user.address, dto.getAddress(), true)
                .andIntegerEq(user.type, dto.getType())
                .build();

        Page<User> userPage = userRepository.findAll(builder, pageable);
        return new PageImpl<>(Mappers.getMapper(UserMapper.class).fromEntitys(userPage.getContent()), pageable, userPage.getTotalElements());
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        QUser qUser = QUser.user;
        Optional<User> optional = userRepository.findOne(qUser.username.eq(username));
        if (optional.isPresent()) {
            return Mappers.getMapper(UserMapper.class).fromEntity(optional.get());
        }
        return null;

    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        UserMapper mapper = Mappers.getMapper(UserMapper.class);
        dto.setType(0);
        return mapper.fromEntity(userRepository.saveAndFlush(mapper.toEntity(dto)));
    }

    @Override
    public void modifyManager(UserDTO dto, Long id) {
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
    public boolean validate(Long id, Integer type) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent() && optional.get().getType() >= type) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateById(Long id, Long mId) {
        //操作用户
        Optional<User> user1 = userRepository.findById(id);
        //被更改的用户
        Optional<User> user2 = userRepository.findById(mId);

        if (user1.isPresent() && user2.isPresent() && user1.get().getType() >= user2.get().getType()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void batchDeleteUser(Long[] ids) {
        if (ids != null && ids.length > 1) {
            List<User> list = new ArrayList<>(ids.length);
            for (Long id : ids) {
                User user = new User();
                user.setId(id);
                list.add(user);
            }
            userRepository.deleteAll(list);
        }
    }

    @Override
    public void freezeUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setStatus(FREEZED_STATUS);
            userRepository.saveAndFlush(user);
        });
    }

    @Override
    public String changeRole(Long mId, String role) {
        Optional<User> user = userRepository.findById(mId);
        if (user.isPresent()) {
            user.get().setRole(role);
            userRepository.saveAndFlush(user.get());
            return user.get().getUsername();
        }
        return null;
    }

    @Override
    public UserDTO searchUserById(Long id) {
        final UserDTO[] model = {null};
        userRepository.findById(id).ifPresent(o -> model[0] = Mappers.getMapper(UserMapper.class).fromEntity(o));
        return model[0];
    }

    @Override
    public UserDTO thawUser(Long mId) {
        Optional<User> optional = userRepository.findById(mId);
        if (optional.isPresent()) {
            return Mappers.getMapper(UserMapper.class).fromEntity(userRepository.saveAndFlush(optional.get()));
        }
        throw new RuntimeException("解冻用户失败");
    }

    @Override
    public List<UserDTO> getUsersHasNoOrg() {
        return userRepository.findAll().parallelStream().filter(o -> Objects.isNull(o.getOrganization()))
                .map(o -> Mappers.getMapper(UserMapper.class).fromEntity(o)).collect(Collectors.toList());
    }

    @Override
    public Integer saveUserVerification(UserVerificationDTO body) {
        Optional<UserVerification> optional = userVerificationRepository.findOne(QUserVerification.userVerification.userId.eq(body.getUserId()));
        if (optional.isPresent()) {
            UserVerification entity = optional.get();
            body.setId(entity.getId());
            body.setAuditTime(null);
            body.setStatus(entity.getStatus());
            body.setCreateTime(entity.getCreateTime());
        }
        return userVerificationRepository.saveAndFlush(UserVerificationMapper.MAPPER.toEntity(body)).getId();
    }

}
