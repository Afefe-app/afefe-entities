package com.ocean.afefe.entities.modules.analytics.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType implements Enumerable {

    COURSE("course purchase"),
    REVIEW_RATING("review and rating"),
    ;
    private final String description;

}
