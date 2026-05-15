package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.common.Status;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA {@link Specification} helpers for superadmin learner lists and filters.
 */
public final class LearnerSpecifications {

    private LearnerSpecifications() {
    }

    public static Specification<User> platformLearners() {
        return (root, query, cb) -> cb.equal(root.get("userType"), UserType.PLATFORM_LEARNER);
    }

    public static Specification<User> searchFullNameOrEmail(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String p = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(cb.coalesce(root.get("fullName"), "")), p),
                cb.like(cb.lower(cb.coalesce(root.get("emailAddress"), "")), p)
        );
    }

    /** B2B = has at least one {@link OrgMember} with {@code joinedAt} not null. */
    public static Specification<User> learnerTypeB2B() {
        return (root, query, cb) -> {
            Subquery<OrgMember> sq = query.subquery(OrgMember.class);
            Root<OrgMember> om = sq.from(OrgMember.class);
            sq.select(om);
            sq.where(
                    cb.equal(om.get("user"), root),
                    cb.isNotNull(om.get("joinedAt"))
            );
            return cb.exists(sq);
        };
    }

    public static Specification<User> learnerTypeB2C() {
        return (root, query, cb) -> {
            Subquery<OrgMember> sq = query.subquery(OrgMember.class);
            Root<OrgMember> om = sq.from(OrgMember.class);
            sq.select(om);
            sq.where(
                    cb.equal(om.get("user"), root),
                    cb.isNotNull(om.get("joinedAt"))
            );
            return cb.not(cb.exists(sq));
        };
    }

    public static Specification<User> accountStatus(String status) {
        if (status == null || status.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String s = status.trim().toUpperCase();
        return switch (s) {
            case "ACTIVE" -> (root, query, cb) -> cb.isTrue(root.get("isActive"));
            case "INACTIVE" -> (root, query, cb) -> cb.isFalse(root.get("isActive"));
            case "PENDING" -> pendingInvitationOrInactiveProfile();
            default -> (root, query, cb) -> cb.conjunction();
        };
    }

    /**
     * Pending: invited to an org but not yet joined (invitation pending).
     */
    private static Specification<User> pendingInvitationOrInactiveProfile() {
        return (root, query, cb) -> {
            Subquery<OrgMember> sq = query.subquery(OrgMember.class);
            Root<OrgMember> om = sq.from(OrgMember.class);
            sq.select(om);
            sq.where(
                    cb.equal(om.get("user"), root),
                    cb.isNull(om.get("joinedAt")),
                    cb.equal(om.get("invitationStatus"), Status.PENDING)
            );
            return cb.exists(sq);
        };
    }

    public static Specification<User> combine(String learnerType, String search, String status) {
        Specification<User> spec = Specification.where(platformLearners()).and(searchFullNameOrEmail(search));
        if (learnerType != null && !learnerType.isBlank()) {
            if ("B2B".equalsIgnoreCase(learnerType.trim())) {
                spec = spec.and(learnerTypeB2B());
            } else if ("B2C".equalsIgnoreCase(learnerType.trim())) {
                spec = spec.and(learnerTypeB2C());
            }
        }
        spec = spec.and(accountStatus(status));
        return spec;
    }
}
