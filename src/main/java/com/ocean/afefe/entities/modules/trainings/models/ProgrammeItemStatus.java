package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgrammeItemStatus implements Enumerable {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    IN_REVIEW("In Review"),
    APPROVED("Approved"),
    COMPLETED("Completed"),
    OVERDUE("Overdue"),
    OPEN("Open"),
    RESOLVED("Resolved"),
    MET("Met"),
    ACTION_REQUIRED("Action Required"),
    UPCOMING("Upcoming"),
    DRAFT("Draft"),
    SUBMITTED("Submitted"),
    MONITORING("Monitoring");

    private final String description;
}
