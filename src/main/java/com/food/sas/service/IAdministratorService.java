package com.food.sas.service;

import com.food.sas.data.dto.AdministratorDTO;

/**
 * @author Created by ygdxd_admin at 2019-11-04 9:50 PM
 */
public interface IAdministratorService {

    Integer createAdministrator(AdministratorDTO dto);

    AdministratorDTO getAdministrator(String username, String password);
}
