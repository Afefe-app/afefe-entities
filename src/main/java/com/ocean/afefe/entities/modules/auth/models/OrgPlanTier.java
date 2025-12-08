package com.ocean.afefe.entities.modules.auth.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrgPlanTier implements Enumerable {

    TIER1("Tier1"),
    TIER2("Tier2"),
    TIER3("Tier3");
    private final String description;
}
