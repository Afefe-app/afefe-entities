package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.CourseStatus;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * JPA {@link Specification} helpers for the superadmin course management lists.
 */
public final class CourseManagementSpecifications {

    private CourseManagementSpecifications() {
    }

    /** Scopes courses to a single organization (typically the super organization). */
    public static Specification<Course> inOrganization(UUID orgId) {
        return (root, query, cb) -> orgId == null
                ? cb.conjunction()
                : cb.equal(root.get("org").get("id"), orgId);
    }

    /** Filters by the raw {@link CourseStatus}; pass null to skip. */
    public static Specification<Course> hasStatus(CourseStatus status) {
        return (root, query, cb) -> status == null
                ? cb.conjunction()
                : cb.equal(root.get("status"), status);
    }

    /** Case-insensitive search across course title and owner instructor name/email. */
    public static Specification<Course> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }
            String like = "%" + search.trim().toLowerCase(Locale.ENGLISH) + "%";
            Join<Course, Instructor> instructor = root.join("ownerInstructor", JoinType.LEFT);
            Join<Instructor, User> user = instructor.join("user", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.like(cb.lower(root.get("title")), like));
            predicates.add(cb.like(cb.lower(user.get("fullName")), like));
            predicates.add(cb.like(cb.lower(user.get("emailAddress")), like));
            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Combines organization scope, status filter, and free-text search.
     */
    public static Specification<Course> combine(UUID orgId, String search, CourseStatus status) {
        return Specification.where(inOrganization(orgId))
                .and(hasStatus(status))
                .and(search(search));
    }
}
