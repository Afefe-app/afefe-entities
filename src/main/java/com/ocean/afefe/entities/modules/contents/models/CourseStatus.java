package com.ocean.afefe.entities.modules.contents.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseStatus implements Enumerable {

    DRAFT("Draft"),
    PUBLISHED("Published")
    ;
    private final String description;
}
