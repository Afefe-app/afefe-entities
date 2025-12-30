package com.ocean.afefe.entities.core.service;

import com.ocean.afefe.entities.common.TenantUser;
import com.ocean.afefe.entities.modules.auth.models.Organization;

public interface OrganizationService {
    Organization getSuperOrganization();

    Organization getOrganizationOrDefault(TenantUser tenantUser);
}
