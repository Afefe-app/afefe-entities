package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingOjtMilestoneType implements Enumerable {

    START("Start"),
    WEEK("Week"),
    COMPLETION("Completion");

    private final String description;
}
