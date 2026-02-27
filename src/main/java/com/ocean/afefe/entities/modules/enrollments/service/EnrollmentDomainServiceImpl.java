package com.ocean.afefe.entities.modules.enrollments.service;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.service.CourseDomainService;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import com.ocean.afefe.entities.modules.enrollments.repository.EnrollmentRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.MessageUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentDomainServiceImpl implements EnrollmentDomainService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseDomainService courseDomainService;
    private final MessageUtil messageUtil;

    @Override
    public Enrollment validateUserCourseEnrollment(User user, Organization organization, UUID courseId) {
        Course course = courseDomainService.validateCourseExistenceById(courseId, organization);
        return enrollmentRepository.findByUserAndCourse(user, course)
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND,
                        messageUtil.getMessage("enrollment.not.found")));
    }
}
