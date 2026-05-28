package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.common.Status;
import com.ocean.afefe.entities.modules.analytics.model.UserActivity;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import java.util.UUID;

/**
 * Subquery-based user filters for {@link UserActivity} specs.
 * Avoids {@code root.join("user")} so Hibernate does not mis-align ResultSet columns when hydrating rows.
 */
public final class UserActivityUserSubqueries {

    private UserActivityUserSubqueries() {}

    public static Predicate userIdsWithType(
            Root<UserActivity> root, CriteriaQuery<?> query, CriteriaBuilder cb, UserType userType) {
        Subquery<UUID> sq = query.subquery(UUID.class);
        Root<User> u = sq.from(User.class);
        sq.select(u.get("id"));
        sq.where(cb.equal(u.get("userType"), userType));
        return root.get("user").get("id").in(sq);
    }

    public static Predicate userIdsWithActiveFlag(
            Root<UserActivity> root, CriteriaQuery<?> query, CriteriaBuilder cb, boolean active) {
        Subquery<UUID> sq = query.subquery(UUID.class);
        Root<User> u = sq.from(User.class);
        sq.select(u.get("id"));
        sq.where(active ? cb.isTrue(u.get("isActive")) : cb.isFalse(u.get("isActive")));
        return root.get("user").get("id").in(sq);
    }

    public static Predicate searchActionOrMatchingUser(
            Root<UserActivity> root, CriteriaQuery<?> query, CriteriaBuilder cb, String patternLower) {
        Subquery<UUID> userSq = query.subquery(UUID.class);
        Root<User> u = userSq.from(User.class);
        userSq.select(u.get("id"));
        userSq.where(cb.or(
                cb.like(cb.lower(cb.coalesce(u.get("emailAddress"), "")), patternLower),
                cb.like(cb.lower(cb.coalesce(u.get("fullName"), "")), patternLower)));
        return cb.or(
                cb.like(cb.lower(cb.coalesce(root.get("action"), "")), patternLower),
                root.get("user").get("id").in(userSq));
    }

    public static Predicate userIdsB2b(Root<UserActivity> root, CriteriaQuery<?> query, CriteriaBuilder cb, boolean b2b) {
        Subquery<UUID> userSq = query.subquery(UUID.class);
        Root<User> u = userSq.from(User.class);
        Subquery<Long> memberSq = query.subquery(Long.class);
        Root<OrgMember> om = memberSq.from(OrgMember.class);
        memberSq.select(cb.literal(1L));
        memberSq.where(cb.equal(om.get("user"), u), cb.isNotNull(om.get("joinedAt")));
        userSq.select(u.get("id"));
        userSq.where(b2b ? cb.exists(memberSq) : cb.not(cb.exists(memberSq)));
        return root.get("user").get("id").in(userSq);
    }

    public static Predicate userIdsWithPendingInvitation(
            Root<UserActivity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Subquery<UUID> userSq = query.subquery(UUID.class);
        Root<User> u = userSq.from(User.class);
        Subquery<Long> memberSq = query.subquery(Long.class);
        Root<OrgMember> om = memberSq.from(OrgMember.class);
        memberSq.select(cb.literal(1L));
        memberSq.where(
                cb.equal(om.get("user"), u),
                cb.isNull(om.get("joinedAt")),
                cb.equal(om.get("invitationStatus"), Status.PENDING));
        userSq.select(u.get("id"));
        userSq.where(cb.exists(memberSq));
        return root.get("user").get("id").in(userSq);
    }
}
