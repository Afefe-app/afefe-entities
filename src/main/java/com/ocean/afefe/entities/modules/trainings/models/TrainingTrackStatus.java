package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingTrackStatus implements Enumerable {
    DRAFT("Draft"),
    ACTIVE("Active"),
    COMPLETED("Completed"),
    ARCHIVED("Archived");

    private final String description;
}
