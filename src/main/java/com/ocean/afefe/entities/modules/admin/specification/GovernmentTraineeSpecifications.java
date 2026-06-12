package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.Trainee;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/** Cross-tenant trainee rows for government platform reports (tenant orgs only). */
public final class GovernmentTraineeSpecifications {

    private GovernmentTraineeSpecifications() {
    }

    public static Specification<Trainee> excludeSuperOrganization(UUID superOrganizationId) {
        if (superOrganizationId == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.notEqual(root.get("organization").get("id"), superOrganizationId);
    }

    public static Specification<Trainee> organizationId(UUID organizationId) {
        if (organizationId == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return OrganizationTraineeSpecifications.organizationIs(organizationId);
    }

    public static Specification<Trainee> combineForGovernmentReport(
            UUID superOrganizationId,
            UUID organizationId,
            String search,
            OrganizationTraineeSpecifications.ActiveFilter listingStatus) {
        return Specification.where(excludeSuperOrganization(superOrganizationId))
                .and(organizationId(organizationId))
                .and(OrganizationTraineeSpecifications.searchUser(search))
                .and(OrganizationTraineeSpecifications.activeFilter(listingStatus));
    }
}
