package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.Trainee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/** Synthetic global activity rows from trainee onboarding (narrow subset until org-wide event stream exists). */
public final class GovernmentOrganizationDashboardActivitySpecifications {

    private GovernmentOrganizationDashboardActivitySpecifications() {
    }

    public static Specification<Trainee> notOrganization(UUID excludedOrgId) {
        return (root, query, cb) -> cb.notEqual(root.get("organization").get("id"), excludedOrgId);
    }

    public static Specification<Trainee> searchUserNameOrEmail(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String p = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> {
            Join<Object, Object> u = root.join("user", JoinType.INNER);
            return cb.or(
                    cb.like(cb.lower(cb.coalesce(u.get("fullName"), "")), p),
                    cb.like(cb.lower(cb.coalesce(u.get("emailAddress"), "")), p));
        };
    }

    public static Specification<Trainee> listingUserStatus(String status) {
        if (status == null || status.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String s = status.trim().toUpperCase();
        return switch (s) {
            case "ACTIVE" -> (root, query, cb) -> {
                Join<Object, Object> u = root.join("user", JoinType.INNER);
                return cb.isTrue(u.get("isActive"));
            };
            case "INACTIVE" -> (root, query, cb) -> {
                Join<Object, Object> u = root.join("user", JoinType.INNER);
                return cb.isFalse(u.get("isActive"));
            };
            case "PENDING" -> (root, query, cb) -> cb.conjunction();
            default -> (root, query, cb) -> cb.conjunction();
        };
    }

    /** PENDING currently matches all rows; extend when trainee invitation state exists. */
    public static Specification<Trainee> combine(UUID excludedSuperOrgId, String search, String status) {
        return Specification.where(notOrganization(excludedSuperOrgId))
                .and(searchUserNameOrEmail(search))
                .and(listingUserStatus(status));
    }
}
