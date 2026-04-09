package com.ocean.afefe.entities.modules.auth.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuperAdminStatus implements Enumerable {
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    REVOKED("Revoked");

    private final String description;
}
