package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainerProctoredTestCandidateStatus implements Enumerable {
    PENDING("Pending"),
    INVITED("Invited"),
    ACCEPTED("Accepted"),
    DECLINED("Declined");

    private final String description;
}
