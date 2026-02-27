package com.ocean.afefe.entities.modules.enrollments.service;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;

import java.util.UUID;

public interface EnrollmentDomainService {

    /**
     * Validates that the user is enrolled in the given course for the organization.
     * Other services (e.g. learner) can use this to ensure enrollment exists before performing actions.
     *
     * @param user         the learner
     * @param organization the tenant organization
     * @param courseId     the course
     * @return the enrollment
     * @throws com.tensorpoint.toolkit.tpointcore.commons.OmnixApiException RECORD_NOT_FOUND if user is not enrolled in the course
     */
    Enrollment validateUserCourseEnrollment(User user, Organization organization, UUID courseId);
}
