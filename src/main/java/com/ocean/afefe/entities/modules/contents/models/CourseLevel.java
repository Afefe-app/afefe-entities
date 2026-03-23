package com.ocean.afefe.entities.modules.contents.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseLevel implements Enumerable {

    BEGINNER("Beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("Advanced")
    ;
    private final String description;
}
