package com.ocean.afefe.entities.modules.nse.events.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventStatus implements Enumerable {
    DRAFT("Draft"),
    PUBLISHED("Published"),
    CANCELLED("Cancelled"),
    CLOSED("Closed");

    private final String description;
}
