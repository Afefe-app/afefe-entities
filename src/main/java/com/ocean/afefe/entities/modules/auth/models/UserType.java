package com.ocean.afefe.entities.modules.auth.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType implements Enumerable {

    PLATFORM_LEARNER("Learner"),
    PLATFORM_INSTRUCTOR("Instructor"),
    PLATFORM_ADMIN("Platform admin"),
    PLATFORM_HR("Platform HR"),

    PLATFORM_TRAINER("Trainer"),
    PLATFORM_TRAINEE("Trainee")
    ;
    private final String description;
}
