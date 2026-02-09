package com.ocean.afefe.entities.modules.assessment.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizAttemptStatus implements Enumerable {

    IN_PROGRESS("In Progress"),
    SUBMITTED("Submitted"),
    GRADED("Graded");

    private final String description;
}
