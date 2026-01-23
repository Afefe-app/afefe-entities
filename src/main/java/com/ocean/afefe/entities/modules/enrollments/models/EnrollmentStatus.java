package com.ocean.afefe.entities.modules.enrollments.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnrollmentStatus implements Enumerable {

    ENROLLED("Enrolled"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed");
    private final String description;
}
