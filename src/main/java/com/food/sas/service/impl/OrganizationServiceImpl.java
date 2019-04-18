package com.food.sas.service.impl;

import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.data.entity.Organization;
import com.food.sas.data.entity.QOrganization;
import com.food.sas.data.repository.OrganizationRepository;
import com.food.sas.mapper.OrganizationMapper;
import com.food.sas.service.IOrganizationService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by ygdxd_admin at 2019-01-06 10:47 PM
 */
@Service
public class OrganizationServiceImpl implements IOrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EntityManager em;

    @Override
    public Page<OrganizationDTO> searchOrganization(String name, Pageable pageable) {
        QOrganization qOrganization = QOrganization.organization;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(name)) {
            builder.and(qOrganization.name.like(name));
        }
        JPAQuery<Organization> query = queryFactory.select(qOrganization).from(qOrganization).where(builder).orderBy(qOrganization.createDate.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize());

        QueryResults<Organization> page = query.fetchResults();
        return new PageImpl<>(Mappers.getMapper(OrganizationMapper.class).fromEntitys(page.getResults()), pageable, page.getTotal());
    }

    @Override
    public void createOrganization(OrganizationDTO dto) {
        organizationRepository.saveAndFlush(Mappers.getMapper(OrganizationMapper.class).toEntity(dto));
    }

    @Override
    public void modifyOrganization(OrganizationDTO dto, Long id) {
        organizationRepository.findById(id).ifPresent(organization -> {
            organization.setName(dto.getName());
            organizationRepository.saveAndFlush(organization);
        });
    }

    @Override
    public void batchDeleteOrganization(Long[] ids) {
        if (ids != null && ids.length > 0) {
            List<Organization> list = new ArrayList<>(ids.length);
            for (Long id : ids) {
                Organization organization = new Organization();
                organization.setId(id);
                list.add(organization);
            }
            organizationRepository.deleteAll(list);
        }
    }
}
