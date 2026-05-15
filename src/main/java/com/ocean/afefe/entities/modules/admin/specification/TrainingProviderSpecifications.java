package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.UUID;

/**
 * Specifications for government training providers (platform trainers with a {@link Trainer} profile in an org).
 */
public final class TrainingProviderSpecifications {

    private TrainingProviderSpecifications() {
    }

    public static Specification<User> platformTrainer() {
        return (root, query, cb) -> cb.equal(root.get("userType"), UserType.PLATFORM_TRAINER);
    }

    /** Trainer row exists for this user in the given organization (typically super org). */
    public static Specification<User> hasTrainerProfileInOrg(UUID orgId) {
        return (root, query, cb) -> {
            Subquery<Trainer> sq = query.subquery(Trainer.class);
            Root<Trainer> tr = sq.from(Trainer.class);
            sq.select(tr);
            sq.where(cb.equal(tr.get("user"), root), cb.equal(tr.get("org").get("id"), orgId));
            return cb.exists(sq);
        };
    }

    public static Specification<User> createdAtBetween(Instant start, Instant end) {
        return (root, query, cb) -> cb.and(
                cb.isNotNull(root.get("createdAt")),
                cb.greaterThanOrEqualTo(root.get("createdAt"), start),
                cb.lessThan(root.get("createdAt"), end));
    }

    public static Specification<User> combine(UUID orgId, String search, String status) {
        return Specification.where(platformTrainer())
                .and(hasTrainerProfileInOrg(orgId))
                .and(LearnerSpecifications.searchFullNameOrEmail(search))
                .and(LearnerSpecifications.accountStatus(status));
    }
}
