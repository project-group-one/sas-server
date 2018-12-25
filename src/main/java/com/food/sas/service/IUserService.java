package com.food.sas.service;

import com.food.sas.data.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Created by ygdxd_admin at 2018-12-25 7:57 PM
 */
public interface IUserService {

    Page<UserDTO> getUserPage(UserDTO dto, Pageable pageable);
}
