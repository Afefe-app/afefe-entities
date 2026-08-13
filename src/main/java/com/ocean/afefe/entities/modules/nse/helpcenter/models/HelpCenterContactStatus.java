package com.ocean.afefe.entities.modules.nse.helpcenter.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelpCenterContactStatus implements Enumerable {
    NEW("New"),
    ACKNOWLEDGED("Acknowledged"),
    RESOLVED("Resolved");

    private final String description;
}
