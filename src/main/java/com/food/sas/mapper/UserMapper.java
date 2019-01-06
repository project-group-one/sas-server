package com.food.sas.mapper;

import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:26 PM
 */
@Mapper
public interface UserMapper {

    List<UserDTO> fromEntitys(List<User> entitys);

    UserDTO fromEntity(User user);

    User toEntity(UserDTO dto);
}
