package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingContentProgressStatus implements Enumerable {
    NOT_STARTED("Not started"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed");

    private final String description;
}
