package com.food.sas.service;

import com.food.sas.data.dto.OrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Created by ygdxd_admin at 2019-01-06 10:34 PM
 */
public interface IOrganizationService {

    Page<OrganizationDTO> searchOrganization(String name, Pageable pageable);

    void createOrganization(OrganizationDTO dto);

    void modifyOrganization(OrganizationDTO dto, Integer id);

    void batchDeleteOrganization(Integer[] ids);
}
