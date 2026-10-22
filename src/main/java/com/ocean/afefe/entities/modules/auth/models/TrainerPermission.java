package com.ocean.afefe.entities.modules.auth.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Permission keys for the trainer-organisation portal (User Management role matrix).
 * Scoped access is composed from these keys via the trainer permission scope support.
 */
@Getter
@RequiredArgsConstructor
public enum TrainerPermission {
    OVERVIEW_VIEW("View trainer organisation overview"),

    REPORT_VIEW("View trainer reports"),
    REPORT_EXPORT("Export trainer reports"),

    FINANCE_VIEW("View trainer finance"),
    FINANCE_EXPORT("Export trainer finance data"),

    PROGRAMME_VIEW("View programmes"),
    PROGRAMME_MANAGE("Create and edit programmes"),
    PROGRAMME_PUBLISH("Publish programmes"),

    TRAINING_PROVIDER_VIEW("View training provider profile and settings"),
    TRAINING_PROVIDER_MANAGE("Manage training provider settings"),

    USER_VIEW("View organisation users"),
    USER_CREATE("Create organisation users"),
    USER_UPDATE("Update organisation users"),
    USER_DEACTIVATE("Deactivate organisation users"),

    ROLE_VIEW("View organisation roles"),
    ROLE_CREATE("Create organisation roles"),
    ROLE_UPDATE("Update organisation roles"),
    ROLE_DELETE("Delete organisation roles"),

    TRAINEE_VIEW("View trainees"),
    TRAINEE_MANAGE("Manage trainees"),

    CURRICULUM_VIEW("View curriculum"),
    CURRICULUM_MANAGE("Manage curriculum"),

    ATTENDANCE_VIEW("View attendance"),
    ATTENDANCE_MANAGE("Manage attendance"),

    PROCTORED_TEST_VIEW("View proctored tests"),
    PROCTORED_TEST_MANAGE("Manage proctored tests"),

    CALENDAR_VIEW("View calendar"),
    CALENDAR_MANAGE("Manage calendar");

    private final String description;

    public boolean isReadPermission() {
        String n = name();
        return n.endsWith("_VIEW") || n.endsWith("_EXPORT");
    }
}
