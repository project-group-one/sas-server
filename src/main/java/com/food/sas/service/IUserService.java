package com.food.sas.service;

import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:57 PM
 */
public interface IUserService {

    Page<UserDTO> getUserPage(UserDTO dto, Pageable pageable);

    UserDTO createUser(UserDTO dto);

    void modifyManager(UserDTO dto, Integer id);

    boolean validate(Integer id, Integer type);

    boolean validateById(Integer id, Integer mId);

    void batchDeleteUser(Integer[] ids);

    void freezeUser(Integer id);

    void changeRole(Integer mId, Integer role);
}
