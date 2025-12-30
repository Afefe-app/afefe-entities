package com.ocean.afefe.entities.modules.calendar.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalendarEventStatus implements Enumerable {

    PENDING("pending"),
    MISSED("Missed"),
    ONGOING("Ongoing"),
    CANCELED("Cancelled"),
    RESCHEDULED("Rescheduled"),
    COMPLETED("Completed")
    ;
    private final String description;
}
