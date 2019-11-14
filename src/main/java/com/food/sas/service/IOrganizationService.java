package com.food.sas.service;

import com.food.sas.data.dto.AddUserToOrganizationRequest;
import com.food.sas.data.dto.CreateOrganizationRequest;
import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Created by ygdxd_admin at 2019-01-06 10:34 PM
 */
public interface IOrganizationService {

    OrganizationDTO searchOrganization(Long id);

    Page<OrganizationDTO> searchOrganization(String name, Pageable pageable);

    void createOrganization(CreateOrganizationRequest dto, Long creator);

    void modifyOrganization(CreateOrganizationRequest dto, Long id);

    void batchDeleteOrganization(Long id);

    void addUserToOrganization(AddUserToOrganizationRequest request);

    void auditOrganization(Long id, StatusEnum status,String errorMsg);
}
