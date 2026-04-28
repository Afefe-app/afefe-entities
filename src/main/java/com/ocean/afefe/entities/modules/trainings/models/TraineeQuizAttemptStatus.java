package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TraineeQuizAttemptStatus implements Enumerable {
    IN_PROGRESS("In progress"),
    SUBMITTED("Submitted"),
    GRADED("Graded");

    private final String description;
}
