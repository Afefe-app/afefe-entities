package com.ocean.afefe.entities.core.service.impl;

import com.ocean.afefe.entities.common.TenantUser;
import com.ocean.afefe.entities.core.service.OrganizationService;
import com.ocean.afefe.entities.modules.auth.models.*;
import com.ocean.afefe.entities.modules.auth.repository.OrgMemberRepository;
import com.ocean.afefe.entities.modules.auth.repository.OrgRepository;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final static String SUPER_ORG_NAME = "Ocean";
    private final static String ORG_SLUG_PREFIX = "ORG";

    private final OrgRepository orgRepository;
    private final OrgMemberRepository orgMemberRepository;

    @Override
    public Organization getSuperOrganization(){
        Organization organization = orgRepository.findFirstByRole(OrganizationRole.OWNER);
        if(Objects.isNull(organization)){
            organization = providerSuperOwnerOrganization();
            organization = orgRepository.saveAndFlush(organization);
        }
        return organization;
    }

    @Override
    public Organization getOrganizationOrDefault(TenantUser tenantUser) {
        return Optional.ofNullable(tenantUser.getOrganization()).orElse(getSuperOrganization());
    }

    public static Organization providerSuperOwnerOrganization(){
        return Organization.builder()
                .name(SUPER_ORG_NAME)
                .slug(CommonUtil.generateSlug(ORG_SLUG_PREFIX))
                .status(OrganizationStatus.ACTIVE)
                .planTier(OrgPlanTier.TIER3)
                .role(OrganizationRole.OWNER)
                .build();
    }
}
