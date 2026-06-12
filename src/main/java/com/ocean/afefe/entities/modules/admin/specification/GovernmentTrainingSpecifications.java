package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/** Cross-tenant programme ({@link Training}) filters for the government portal. */
public final class GovernmentTrainingSpecifications {

    private GovernmentTrainingSpecifications() {
    }

    public static Specification<Training> searchTitleProgrammeIdOrProvider(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String pattern = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> {
            var trainerJoin = root.join("trainer");
            var userJoin = trainerJoin.join("user");
            return cb.or(
                    cb.like(cb.lower(cb.coalesce(root.get("title"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(root.get("programmeId"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(userJoin.get("fullName"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(userJoin.get("emailAddress"), "")), pattern));
        };
    }

    public static Specification<Training> listingStatus(String status) {
        if (status == null || status.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        try {
            TrainingStatus trainingStatus = TrainingStatus.valueOf(status.trim().toUpperCase());
            return (root, query, cb) -> cb.equal(root.get("status"), trainingStatus);
        } catch (IllegalArgumentException ex) {
            return (root, query, cb) -> cb.conjunction();
        }
    }

    public static Specification<Training> ownedByOrganization(UUID organizationId) {
        if (organizationId == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.equal(root.get("org").get("id"), organizationId);
    }

    /** Government catalogue across all owning organizations (platform + tenants). */
    public static Specification<Training> combineForGovernmentDirectory(
            String search, String listingStatusFilter, UUID organizationId) {
        return Specification.where(searchTitleProgrammeIdOrProvider(search))
                .and(listingStatus(listingStatusFilter))
                .and(ownedByOrganization(organizationId));
    }
}
