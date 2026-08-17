package com.ocean.afefe.entities.modules.nse.admin.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NseAdminPermission implements Enumerable {

    OVERVIEW_VIEW("View dashboard overview"),

    MEMBERS_VIEW("View members"),
    MEMBERS_CREATE("Create members"),
    MEMBERS_UPDATE("Update members"),
    MEMBERS_DELETE("Deactivate members"),
    MEMBERS_EXPORT("Export members"),

    COURSES_VIEW("View courses"),
    COURSES_CREATE("Create courses"),
    COURSES_UPDATE("Update courses"),
    COURSES_DELETE("Archive courses"),
    COURSES_EXPORT("Export courses"),

    EVENTS_VIEW("View events"),
    EVENTS_CREATE("Create events"),
    EVENTS_UPDATE("Update events"),
    EVENTS_DELETE("Cancel or close events"),

    REPORTS_VIEW("View reports"),
    REPORTS_EXPORT("Export reports"),

    SUPPORT_VIEW("View help and support"),
    SUPPORT_UPDATE("Manage help and support"),

    AUDIT_VIEW("View audit trail"),

    NOTIFICATIONS_VIEW("View notifications"),
    NOTIFICATIONS_UPDATE("Manage notifications"),

    ADMIN_VIEW("View admin users"),
    ADMIN_CREATE("Create admin users"),
    ADMIN_UPDATE("Update admin users"),
    ADMIN_DELETE("Deactivate admin users"),

    ROLE_VIEW("View roles"),
    ROLE_CREATE("Create roles"),
    ROLE_UPDATE("Update roles"),
    ROLE_DELETE("Delete roles");

    private final String description;
}
