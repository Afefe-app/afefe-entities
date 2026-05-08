package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TraineeQuizQuestionType implements Enumerable {
    MULTI_CHOICE("Multi choice"),
    TRUE_FALSE("True/False");

    private final String description;
}
