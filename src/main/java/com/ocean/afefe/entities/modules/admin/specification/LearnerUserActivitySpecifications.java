package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.analytics.model.UserActivity;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for {@link UserActivity} rows relevant to platform learners (superadmin dashboard).
 */
public final class LearnerUserActivitySpecifications {

    private LearnerUserActivitySpecifications() {
    }

    public static Specification<UserActivity> forPlatformLearnerActivities() {
        return (root, query, cb) ->
                UserActivityUserSubqueries.userIdsWithType(root, query, cb, UserType.PLATFORM_LEARNER);
    }

    public static Specification<UserActivity> searchActivity(String searchKey) {
        if (searchKey == null || searchKey.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String pattern = "%" + searchKey.trim().toLowerCase() + "%";
        return (root, query, cb) ->
                UserActivityUserSubqueries.searchActionOrMatchingUser(root, query, cb, pattern);
    }

    public static Specification<UserActivity> userTypeMatches(String learnerType) {
        if (learnerType == null || learnerType.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        if ("B2B".equalsIgnoreCase(learnerType.trim())) {
            return (root, query, cb) -> UserActivityUserSubqueries.userIdsB2b(root, query, cb, true);
        }
        if ("B2C".equalsIgnoreCase(learnerType.trim())) {
            return (root, query, cb) -> UserActivityUserSubqueries.userIdsB2b(root, query, cb, false);
        }
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification<UserActivity> statusMatches(String status) {
        if (status == null || status.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String s = status.trim().toUpperCase();
        return switch (s) {
            case "ACTIVE" -> (root, query, cb) ->
                    UserActivityUserSubqueries.userIdsWithActiveFlag(root, query, cb, true);
            case "INACTIVE" -> (root, query, cb) ->
                    UserActivityUserSubqueries.userIdsWithActiveFlag(root, query, cb, false);
            case "PENDING" -> (root, query, cb) ->
                    UserActivityUserSubqueries.userIdsWithPendingInvitation(root, query, cb);
            default -> (root, query, cb) -> cb.conjunction();
        };
    }
}
