package com.food.sas.service.impl;

import com.food.sas.data.dto.AdministratorDTO;
import com.food.sas.data.entity.QAdministrator;
import com.food.sas.data.repository.AdministratorRepository;
import com.food.sas.mapper.AdministratorMapper;
import com.food.sas.service.IAdministratorService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by ygdxd_admin at 2019-11-04 10:01 PM
 */
@Service
public class AdministratorServiceImpl implements IAdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;


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
}
