package com.ocean.afefe.entities.modules.auth.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationRole implements Enumerable {
    OWNER("Owner"),
    PARTNER("Partner")
    ;
    private final String description;
}
