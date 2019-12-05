package com.food.sas.service;

import com.food.sas.data.dto.AddUserToOrganizationRequest;
import com.food.sas.data.dto.CreateOrganizationRequest;
import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.data.dto.OrganizationModel;
import com.food.sas.enums.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Created by ygdxd_admin at 2019-01-06 10:34 PM
 */
public interface IOrganizationService {

    OrganizationDTO searchOrganization(Long id);

    Page<OrganizationModel> searchOrganization(String name, Pageable pageable);

    OrganizationModel createOrganization(CreateOrganizationRequest dto, Long creator);

    void modifyOrganization(CreateOrganizationRequest dto, Long id);

    void batchDeleteOrganization(Long id);

    void addUserToOrganization(AddUserToOrganizationRequest request);

    void auditOrganization(Long id, StatusEnum status, String errorMsg);

    OrganizationModel getOrganizationByUser(Long userId);
}
