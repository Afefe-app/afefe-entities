package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumStatus;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumSubmission;
import org.springframework.data.jpa.domain.Specification;

import java.util.EnumSet;
import java.util.Set;

/** Government curriculum submission queue filters (super-org trainer submissions). */
public final class GovernmentCurriculumSubmissionSpecifications {

    private static final Set<TrainerCurriculumStatus> PENDING_STATUSES = EnumSet.of(
            TrainerCurriculumStatus.SUBMITTED,
            TrainerCurriculumStatus.UNDER_REVIEW,
            TrainerCurriculumStatus.FEEDBACK,
            TrainerCurriculumStatus.FLAGGED);

    private GovernmentCurriculumSubmissionSpecifications() {
    }

    public static Specification<TrainerCurriculumSubmission> forOrganization(Organization organization) {
        if (organization == null) {
            return (root, query, cb) -> cb.conjunction();
        }
        return (root, query, cb) -> cb.equal(root.get("org"), organization);
    }

    public static Specification<TrainerCurriculumSubmission> searchQueue(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String pattern = "%" + search.trim().toLowerCase() + "%";
        return (root, query, cb) -> {
            var trainerJoin = root.join("trainer");
            var userJoin = trainerJoin.join("user");
            return cb.or(
                    cb.like(cb.lower(cb.coalesce(root.get("curriculumCode"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(root.get("title"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(root.get("programmeId"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(root.get("category"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(userJoin.get("fullName"), "")), pattern),
                    cb.like(cb.lower(cb.coalesce(userJoin.get("emailAddress"), "")), pattern));
        };
    }

    public static Specification<TrainerCurriculumSubmission> listingStatus(String status) {
        if (status == null || status.isBlank() || "ALL".equalsIgnoreCase(status.trim())) {
            return (root, query, cb) -> cb.conjunction();
        }
        String normalized = status.trim().toUpperCase();
        if ("PENDING".equals(normalized)) {
            return (root, query, cb) -> root.get("status").in(PENDING_STATUSES);
        }
        try {
            TrainerCurriculumStatus curriculumStatus = TrainerCurriculumStatus.valueOf(normalized);
            return (root, query, cb) -> cb.equal(root.get("status"), curriculumStatus);
        } catch (IllegalArgumentException ex) {
            return (root, query, cb) -> cb.conjunction();
        }
    }

    public static Specification<TrainerCurriculumSubmission> combineForGovernmentQueue(
            Organization organization, String search, String listingStatusFilter) {
        return Specification.where(forOrganization(organization))
                .and(searchQueue(search))
                .and(listingStatus(listingStatusFilter));
    }
}
