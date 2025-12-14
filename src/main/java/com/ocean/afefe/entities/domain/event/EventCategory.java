package com.ocean.afefe.entities.domain.event;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventCategory implements Enumerable {

    SPRING("Spring"),
    KAFKA("Kafka");

    private final String description;
}
