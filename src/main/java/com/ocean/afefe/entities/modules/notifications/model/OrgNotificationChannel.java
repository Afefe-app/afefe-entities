package com.ocean.afefe.entities.modules.notifications.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrgNotificationChannel implements Enumerable {
    EMAIL("Email"),
    IN_APP("In-App");

    private final String description;
}
