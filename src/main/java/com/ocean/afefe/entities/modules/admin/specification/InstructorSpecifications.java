package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA {@link Specification} helpers for superadmin instructor lists.
 */
public final class InstructorSpecifications {

    private InstructorSpecifications() {
    }

    public static Specification<User> platformInstructors() {
        return (root, query, cb) -> cb.equal(root.get("userType"), UserType.PLATFORM_INSTRUCTOR);
    }

    /**
     * Filters platform instructors by search (name/email) and account status (ACTIVE/INACTIVE/PENDING).
     * Reuses learner-oriented search/status predicates because they apply to {@link User} generically.
     */
    public static Specification<User> combine(String search, String status) {
        return Specification.where(platformInstructors())
                .and(LearnerSpecifications.searchFullNameOrEmail(search))
                .and(LearnerSpecifications.accountStatus(status));
    }
}
