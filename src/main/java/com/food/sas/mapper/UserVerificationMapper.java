package com.food.sas.mapper;

import com.food.sas.data.dto.UserVerificationDTO;
import com.food.sas.data.entity.UserVerification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Created by ygdxd_admin at 2019-11-26 9:08 PM
 */
@Mapper
public interface UserVerificationMapper {
    UserVerificationMapper MAPPER = Mappers.getMapper(UserVerificationMapper.class);

    UserVerification toEntity(UserVerificationDTO dto);

    UserVerificationDTO fromEntity(UserVerification entity);

}
