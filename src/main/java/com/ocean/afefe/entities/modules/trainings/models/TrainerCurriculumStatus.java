package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainerCurriculumStatus implements Enumerable {
    SUBMITTED("Submitted"),
    UNDER_REVIEW("Under review"),
    FEEDBACK("Feedback"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    FLAGGED("Flagged");

    private final String description;
}
