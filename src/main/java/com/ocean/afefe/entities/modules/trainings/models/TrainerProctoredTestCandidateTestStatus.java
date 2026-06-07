package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainerProctoredTestCandidateTestStatus implements Enumerable {
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    TERMINATED("Terminated");

    private final String description;
}
