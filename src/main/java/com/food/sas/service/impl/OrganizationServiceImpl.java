package com.food.sas.service.impl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.food.sas.data.dto.AddUserToOrganizationRequest;
import com.food.sas.data.dto.CreateOrganizationRequest;
import com.food.sas.data.dto.OrganizationDTO;
import com.food.sas.data.dto.OrganizationModel;
import com.food.sas.data.entity.Organization;
import com.food.sas.data.entity.QOrganization;
import com.food.sas.data.entity.QUser;
import com.food.sas.data.entity.User;
import com.food.sas.data.repository.OrganizationRepository;
import com.food.sas.data.repository.UserRepository;
import com.food.sas.enums.StatusEnum;
import com.food.sas.exception.BadException;
import com.food.sas.mapper.OrganizationMapper;
import com.food.sas.service.IOrganizationService;
import com.google.common.collect.Sets;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Created by ygdxd_admin at 2019-01-06 10:47 PM
 */
@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements IOrganizationService {

    private final OrganizationRepository organizationRepository;

    private final UserRepository userRepository;

    private final EntityManager em;

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public OrganizationDTO searchOrganization(Long id) {
        Optional<Organization> optional = organizationRepository.findById(id, EntityGraphUtils.fromName(Organization.ALL, true));
        if (!optional.isPresent()) {
            throw new BadException("不存该");
        }
        return Mappers.getMapper(OrganizationMapper.class).fromEntity(optional.get());
    }

    @Override
    public Page<OrganizationModel> searchOrganization(String name, Pageable pageable) {
        QOrganization qOrganization = QOrganization.organization;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(name)) {
            builder.and(qOrganization.name.like(name));
        }
        JPAQuery<Organization> query = queryFactory.select(qOrganization).from(qOrganization).where(builder).orderBy(qOrganization.createDate.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize());

        QueryResults<Organization> page = query.fetchResults();
        return new PageImpl<>(Mappers.getMapper(OrganizationMapper.class).toModels(page.getResults()), pageable, page.getTotal());
    }

    @Override
    public OrganizationModel createOrganization(CreateOrganizationRequest body, Long creator) {
        boolean existsByName = organizationRepository.existsByName(body.getName());
        if (existsByName) {
            throw new BadException("已经存在该组织名称");
        }
        User user = userRepository.findById(creator).get();
        Organization organization = Organization.builder()
                .name(body.getName())
                .creator(creator)
                .users(Sets.newHashSet(user))
                .credential(body.getCredential())
                .status(StatusEnum.WAIT_AUDIT)
                .build();
        Organization doc = organizationRepository.saveAndFlush(organization);
        return Mappers.getMapper(OrganizationMapper.class).toModel(doc);
    }

    @Override
    public void modifyOrganization(CreateOrganizationRequest dto, Long id) {
        organizationRepository.findById(id).ifPresent(organization -> {
            organization.setName(dto.getName());
            organizationRepository.saveAndFlush(organization);
        });
    }

    @Override
    public void batchDeleteOrganization(Long id) {
        Optional<Organization> optional = organizationRepository.findById(id);
        optional.ifPresent(organization -> {
            Set<User> users = organization.getUsers();
            if (!CollectionUtils.isEmpty(users)) {
                throw new BadException("组织里面包含成员不能直接删除，请移除相关人员");
            }
            organizationRepository.deleteById(id);
        });
    }

    @Override
    public void addUserToOrganization(AddUserToOrganizationRequest request) {
        Optional<Organization> organizationOptional = organizationRepository.findById(request.getOrgId(), EntityGraphUtils.fromName(Organization.ALL, true));
        if (!organizationOptional.isPresent()) {
            throw new BadException("该组织不存在");
        }
        List<User> users = userRepository.findByIdIn(request.getUserIds());
        if (CollectionUtils.isEmpty(users)) {
            throw new BadException("该用户不存在");
        }
        Organization organization = organizationOptional.get();
        users.forEach(o -> o.setOrganization(organization));
        organization.getUsers().addAll(users);
        organizationRepository.save(organization);
    }

    @Override
    public void auditOrganization(Long id, StatusEnum status, String errorMsg) {
        Optional<Organization> organizationOptional = organizationRepository.findById(id);
        if (!organizationOptional.isPresent()) {
            throw new BadException("该组织不存在");
        }
        Organization organization = organizationOptional.get();
        if (organization.getStatus() == StatusEnum.AUDITING) {
            throw new BadException("不在审核中不需要审核");
        }
        organization.setStatus(status);
        organization.setErrorMsg(errorMsg);
        organizationRepository.save(organization);
    }

    @Override
    public OrganizationModel getOrganizationByUser(Long userId) {
        QOrganization organization = QOrganization.organization;
        QUser user = QUser.user;
        BooleanExpression eq = user.id.eq(userId);
        Organization org = jpaQueryFactory.selectFrom(organization)
                .leftJoin(organization.users, user)
                .where(eq).fetchFirst();
        return Mappers.getMapper(OrganizationMapper.class).toModel(org);
    }
}
