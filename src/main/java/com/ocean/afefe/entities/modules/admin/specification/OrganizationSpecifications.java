package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.OrganizationStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/** Government organization directory filters (tenant org rows). */
public final class OrganizationSpecifications {

    private OrganizationSpecifications() {
    }

    public static Specification<Organization> excludeId(UUID excludedOrgId) {
        return (root, query, cb) -> cb.notEqual(root.get("id"), excludedOrgId);
    }

    public static Specification<Organization> searchNameOrSlug(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String p = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(cb.coalesce(root.get("name"), "")), p),
                cb.like(cb.lower(cb.coalesce(root.get("slug"), "")), p),
                cb.like(cb.lower(cb.coalesce(root.get("contactEmail"), "")), p));
    }

    public static Specification<Organization> listingStatus(String status) {
        if (status == null || status.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String s = status.trim().toUpperCase();
        return switch (s) {
            case "ACTIVE" -> (root, query, cb) -> cb.equal(root.get("status"), OrganizationStatus.ACTIVE);
            case "INACTIVE" -> (root, query, cb) -> cb.equal(root.get("status"), OrganizationStatus.INACTIVE);
            case "PENDING" -> (root, query, cb) -> cb.equal(root.get("status"), OrganizationStatus.PENDING);
            default -> (root, query, cb) -> cb.conjunction();
        };
    }

    /** Combine excludes super platform org row and applies search/status for government list. */
    public static Specification<Organization> combineForGovernmentDirectory(UUID excludedSuperOrganizationId,
                                                                              String search,
                                                                              String listingStatusFilter) {
        return Specification.where(excludeId(excludedSuperOrganizationId))
                .and(searchNameOrSlug(search))
                .and(listingStatus(listingStatusFilter));
    }
}
