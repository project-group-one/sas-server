package com.food.sas.service.impl;

import com.food.sas.data.dto.AdministratorDTO;
import com.food.sas.data.dto.UserVerificationDTO;
import com.food.sas.data.entity.QAdministrator;
import com.food.sas.data.entity.QUserVerification;
import com.food.sas.data.entity.User;
import com.food.sas.data.entity.UserVerification;
import com.food.sas.data.repository.AdministratorRepository;
import com.food.sas.data.repository.UserRepository;
import com.food.sas.data.repository.UserVerificationRepository;
import com.food.sas.mapper.AdministratorMapper;
import com.food.sas.mapper.UserVerificationMapper;
import com.food.sas.service.IAdministratorService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Created by ygdxd_admin at 2019-11-04 10:01 PM
 */
@Service
public class AdministratorServiceImpl implements IAdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private UserVerificationRepository userVerificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer createAdministrator(AdministratorDTO dto) {
        return administratorRepository.save(Mappers.getMapper(AdministratorMapper.class).toEntity(dto)).getId();
    }

    @Override
    public AdministratorDTO getAdministrator(String username, String password) {
        QAdministrator qAdministrator = QAdministrator.administrator;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qAdministrator.username.eq(username)).and(qAdministrator.password.eq(password));
        return Mappers.getMapper(AdministratorMapper.class).fromEntity(administratorRepository.findOne(builder).orElse(null));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean verifyUser(Integer id) {
        Optional<UserVerification> optional = userVerificationRepository.findById(id);
        if (optional.isPresent()) {
            UserVerification entity = optional.get();
            Optional<User> userOptional = userRepository.findById(entity.getUserId());
            if (userOptional.isPresent()) {
                entity.setStatus(1);
                User user = userOptional.get();
                user.setVerifyStatus(2);
                userVerificationRepository.saveAndFlush(entity).getUserId();
                userRepository.saveAndFlush(user);
                return Boolean.TRUE;
            }
        }
        return false;
    }

    @Override
    public UserVerificationDTO getUserVerification(Long i) {
        QUserVerification qUserVerification = QUserVerification.userVerification;
        Optional<UserVerification> optional =
                userVerificationRepository.findOne(qUserVerification.userId.eq(i).and(qUserVerification.status.eq(0)));
        if (optional.isPresent()) {
            return UserVerificationMapper.MAPPER.fromEntity(optional.get());
        }
        return null;
    }
}
