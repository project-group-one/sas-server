package com.food.sas.mapper;

import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.data.entity.Organization;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-08 8:18 PM
 */
@Mapper
public interface OrganizationMapper {

    Organization toEntity(OrganizationDTO dto);

    OrganizationDTO fromEntity(Organization entity);

    List<Organization> toEntitys(List<OrganizationDTO> dtos);

    List<OrganizationDTO> fromEntitys(List<Organization> entitys);
}
