package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.Trainee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public final class OrganizationTraineeSpecifications {

    private OrganizationTraineeSpecifications() {
    }

    public static Specification<Trainee> organizationIs(UUID organizationId) {
        return (root, query, cb) -> cb.equal(root.get("organization").get("id"), organizationId);
    }

    public static Specification<Trainee> searchUser(String search) {
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

    public static Specification<Trainee> activeFilter(ActiveFilter f) {
        if (f == null || f == ActiveFilter.ALL) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> {
            Join<Object, Object> u = root.join("user", JoinType.INNER);
            return f == ActiveFilter.ACTIVE ? cb.isTrue(u.get("isActive")) : cb.isFalse(u.get("isActive"));
        };
    }

    public enum ActiveFilter {
        ALL,
        ACTIVE,
        INACTIVE
    }

    public static Specification<Trainee> combine(UUID organizationId, String search, ActiveFilter listingStatus) {
        return Specification.where(organizationIs(organizationId))
                .and(searchUser(search))
                .and(activeFilter(listingStatus));
    }
}
