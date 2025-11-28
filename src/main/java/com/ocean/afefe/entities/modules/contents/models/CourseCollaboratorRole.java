package com.ocean.afefe.entities.modules.contents.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseCollaboratorRole {
    OWNER("OWNER"),
    CO_AUTHOR("CO_AUTHOR"),
    EDITOR("EDITOR"),
    REVIEWER("REVIEWER"),;

    private final String description;
}
