package com.ocean.afefe.entities.modules.nse.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingContentItemFormat implements Enumerable {
    VIDEO("Video"),
    READING("Reading"),
    PRACTICE_QUIZ("Practice quiz"),
    MIXED("Mixed");

    private final String description;
}
