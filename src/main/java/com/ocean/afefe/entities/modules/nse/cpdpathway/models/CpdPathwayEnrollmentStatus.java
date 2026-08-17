package com.ocean.afefe.entities.modules.nse.cpdpathway.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CpdPathwayEnrollmentStatus implements Enumerable {
    PENDING_PAYMENT("Pending payment"),
    ENROLLED("Enrolled"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String description;
}
