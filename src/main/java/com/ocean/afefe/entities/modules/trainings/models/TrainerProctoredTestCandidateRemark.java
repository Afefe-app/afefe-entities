package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainerProctoredTestCandidateRemark implements Enumerable {
    PASS("Pass"),
    FAIL("Fail"),
    NIL("Nil");

    private final String description;
}
