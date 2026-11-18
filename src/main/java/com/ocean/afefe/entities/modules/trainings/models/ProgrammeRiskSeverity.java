package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgrammeRiskSeverity implements Enumerable {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private final String description;
}
