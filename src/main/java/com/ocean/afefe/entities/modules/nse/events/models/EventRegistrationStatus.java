package com.ocean.afefe.entities.modules.nse.events.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventRegistrationStatus implements Enumerable {
    PENDING_PAYMENT("Pending payment"),
    REGISTERED("Registered"),
    CANCELLED("Cancelled"),
    EXPIRED("Expired");

    private final String description;
}
