package com.ocean.afefe.entities.modules.calendar.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CalendarEventType implements Enumerable {

    DEFAULT("Default"),
    VIDEO_CONFERENCING("Video conferencing")
    ;
    private final String description;
}
