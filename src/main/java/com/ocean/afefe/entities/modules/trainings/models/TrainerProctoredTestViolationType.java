package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainerProctoredTestViolationType implements Enumerable {
    TAB_APP_SWITCH("Tab/App switches"),
    NOISE_INCIDENT("Noise incidents"),
    MULTIPLE_FACE("Multiple faces");

    private final String description;
}
