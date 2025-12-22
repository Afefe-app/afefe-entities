package com.ocean.afefe.entities.modules.assessment.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionType implements Enumerable {

    MULTI_CHOICE("Multi-choice"),
    TRUE_FALSE("True/False");
    private final String description;
}
