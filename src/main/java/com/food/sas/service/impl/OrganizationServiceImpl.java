package com.food.sas.service.impl;

import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.data.repository.OrganizationRepository;
import com.food.sas.service.IOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Created by ygdxd_admin at 2019-01-06 10:47 PM
 */
@Service
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public Page<OrganizationDTO> searchOrganization(String name, Pageable pageable) {
        return null;
    }

    @Override
    public void createOrganization(OrganizationDTO dto) {

    }

    @Override
    public void modifyOrganization(OrganizationDTO dto, Integer id) {

    }

    @Override
    public void batchDeleteOrganization(Integer[] ids) {

    }
}
