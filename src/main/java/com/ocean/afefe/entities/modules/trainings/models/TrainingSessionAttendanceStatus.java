package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingSessionAttendanceStatus implements Enumerable {

    PRESENT("Present"),
    LATE("Late");

    private final String description;
}
