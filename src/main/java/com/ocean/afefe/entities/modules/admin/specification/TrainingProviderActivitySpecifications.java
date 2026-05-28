package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.analytics.model.UserActivity;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
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
            Subquery<UUID> sq = query.subquery(UUID.class);
            Root<User> u = sq.from(User.class);
            Subquery<Long> trainerSq = query.subquery(Long.class);
            Root<Trainer> tr = trainerSq.from(Trainer.class);
            trainerSq.select(cb.literal(1L));
            trainerSq.where(cb.equal(tr.get("user"), u), cb.equal(tr.get("org").get("id"), orgId));
            sq.select(u.get("id"));
            sq.where(cb.equal(u.get("userType"), UserType.PLATFORM_TRAINER), cb.exists(trainerSq));
            return root.get("user").get("id").in(sq);
        };
    }

    public static Specification<UserActivity> searchActivity(String searchKey) {
        if (searchKey == null || searchKey.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String pattern = "%" + searchKey.trim().toLowerCase() + "%";
        return (root, query, cb) ->
                UserActivityUserSubqueries.searchActionOrMatchingUser(root, query, cb, pattern);
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

    public static Specification<UserActivity> combine(UUID orgId, String search, String status) {
        return Specification.where(forTrainingProvidersInOrg(orgId))
                .and(searchActivity(search))
                .and(statusMatches(status));
    }
}
