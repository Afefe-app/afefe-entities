package com.ocean.afefe.entities.modules.appuser.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppUserStatus {
    ACTIVE(1, "Active"),
    DISABLED(2, "Disabled");

    private final Integer id;
    private final String desc;
}
