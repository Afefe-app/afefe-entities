package com.ocean.afefe.entities.modules.notifications.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrgNotificationStatus implements Enumerable {
    DRAFT("Drafted"),
    SCHEDULED("Scheduled"),
    SENT("Sent"),
    FAILED("Failed");

    private final String description;
}
