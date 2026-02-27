package com.ocean.afefe.entities.modules.enrollments;

import com.ocean.afefe.entities.modules.enrollments.service.EnrollmentDomainServiceImpl;
import org.springframework.context.annotation.Import;

@Import({
        EnrollmentDomainServiceImpl.class
})
public class EnrollmentModule {
}
