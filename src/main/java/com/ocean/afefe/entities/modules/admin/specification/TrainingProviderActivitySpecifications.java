package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.common.Status;
import com.ocean.afefe.entities.modules.analytics.model.UserActivity;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/** {@link UserActivity} rows scoped to training providers in a given organization. */
public final class TrainingProviderActivitySpecifications {

    private TrainingProviderActivitySpecifications() {
    }

    public static Specification<UserActivity> forTrainingProvidersInOrg(UUID orgId) {
        return (root, query, cb) -> {
            Join<UserActivity, User> u = root.join("user", JoinType.INNER);
            Predicate trainerType = cb.equal(u.get("userType"), UserType.PLATFORM_TRAINER);
            Subquery<Trainer> sq = query.subquery(Trainer.class);
            Root<Trainer> tr = sq.from(Trainer.class);
            sq.select(tr);
            sq.where(cb.equal(tr.get("user"), u), cb.equal(tr.get("org").get("id"), orgId));
            return cb.and(trainerType, cb.exists(sq));
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
                    cb.equal(om.get("invitationStatus"), Status.PENDING)
            );
            return cb.exists(sq);
        };
    }

    public static Specification<UserActivity> combine(UUID orgId, String search, String status) {
        return Specification.where(forTrainingProvidersInOrg(orgId))
                .and(searchActivity(search))
                .and(statusMatches(status));
    }
}
