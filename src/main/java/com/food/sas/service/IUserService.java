package com.food.sas.service;

import com.food.sas.data.dto.UserDTO;
import com.food.sas.data.dto.UserVerificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:57 PM
 */
public interface IUserService {

    Page<UserDTO> getUserPage(UserDTO dto, Pageable pageable);

    UserDTO findUserByUsername(String username);

    UserDTO createUser(UserDTO dto);

    void modifyManager(UserDTO dto, Long id);

    boolean validate(Long id, Integer type);

    boolean validateById(Long id, Long mId);

    void batchDeleteUser(Long[] ids);

    void freezeUser(Long id);

    String changeRole(Long mId, String role);

    UserDTO searchUserById(Long id);

    UserDTO thawUser(Long mId);

    List<UserDTO> getUsersHasNoOrg();

    Integer saveUserVerification(UserVerificationDTO body);

    List<String> getUserIdsByStatus(Integer status);
}
