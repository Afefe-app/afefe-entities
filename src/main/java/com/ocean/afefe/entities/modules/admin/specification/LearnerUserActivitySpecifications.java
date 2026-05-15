package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.analytics.model.UserActivity;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for {@link UserActivity} rows relevant to platform learners (superadmin dashboard).
 */
public final class LearnerUserActivitySpecifications {

    private LearnerUserActivitySpecifications() {
    }

    public static Specification<UserActivity> forPlatformLearnerActivities() {
        return (root, query, cb) -> {
            Join<UserActivity, User> userJoin = root.join("user", JoinType.INNER);
            return cb.equal(userJoin.get("userType"), UserType.PLATFORM_LEARNER);
        };
    }

    public static Specification<UserActivity> searchActivity(String searchKey) {
        if (searchKey == null || searchKey.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String p = "%" + searchKey.trim().toLowerCase() + "%";
        return (root, query, cb) -> {
            Join<UserActivity, User> u = root.join("user", JoinType.LEFT);
            Predicate actionMatch = cb.like(cb.lower(cb.coalesce(root.get("action"), "")), p);
            Predicate emailMatch = cb.like(cb.lower(cb.coalesce(u.get("emailAddress"), "")), p);
            Predicate nameMatch = cb.like(cb.lower(cb.coalesce(u.get("fullName"), "")), p);
            return cb.or(actionMatch, emailMatch, nameMatch);
        };
    }

    public static Specification<UserActivity> userTypeMatches(String learnerType) {
        if (learnerType == null || learnerType.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        if ("B2B".equalsIgnoreCase(learnerType.trim())) {
            return (root, query, cb) -> {
                Join<UserActivity, User> u = root.join("user", JoinType.INNER);
                Subquery<OrgMember> sq = query.subquery(OrgMember.class);
                Root<OrgMember> om = sq.from(OrgMember.class);
                sq.select(om);
                sq.where(cb.equal(om.get("user"), u), cb.isNotNull(om.get("joinedAt")));
                return cb.exists(sq);
            };
        }
        if ("B2C".equalsIgnoreCase(learnerType.trim())) {
            return (root, query, cb) -> {
                Join<UserActivity, User> u = root.join("user", JoinType.INNER);
                Subquery<OrgMember> sq = query.subquery(OrgMember.class);
                Root<OrgMember> om = sq.from(OrgMember.class);
                sq.select(om);
                sq.where(cb.equal(om.get("user"), u), cb.isNotNull(om.get("joinedAt")));
                return cb.not(cb.exists(sq));
            };
        }
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<UserActivity> statusMatches(String status) {
        if (status == null || status.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String s = status.trim().toUpperCase();
        return switch (s) {
            case "ACTIVE" -> (root, query, cb) -> {
                Join<UserActivity, User> u = root.join("user", JoinType.INNER);
                return cb.isTrue(u.get("isActive"));
            };
            case "INACTIVE" -> (root, query, cb) -> {
                Join<UserActivity, User> u = root.join("user", JoinType.INNER);
                return cb.isFalse(u.get("isActive"));
            };
            case "PENDING" -> pendingInvitation();
            default -> (root, query, cb) -> cb.conjunction();
        };
    }

    private static Specification<UserActivity> pendingInvitation() {
        return (root, query, cb) -> {
            Join<UserActivity, User> u = root.join("user", JoinType.INNER);
            Subquery<OrgMember> sq = query.subquery(OrgMember.class);
            Root<OrgMember> om = sq.from(OrgMember.class);
            sq.select(om);
            sq.where(
                    cb.equal(om.get("user"), u),
                    cb.isNull(om.get("joinedAt")),
                    cb.equal(om.get("invitationStatus"), com.ocean.afefe.entities.common.Status.PENDING)
            );
            return cb.exists(sq);
        };
    }
}
