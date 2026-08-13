package com.ocean.afefe.entities.modules.auth.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GovernmentPermission {
    OVERVIEW_VIEW("View government overview dashboard"),

    ORGANIZATION_VIEW("View organizations"),
    ORGANIZATION_UPDATE("Update organization profile"),
    ORGANIZATION_NOTIFY("Send organization notifications"),
    ORGANIZATION_TRAINEE_REMIND("Send trainee reminders"),
    ORGANIZATION_TRAINEE_REMOVE("Remove trainees from organization"),
    ORGANIZATION_DEACTIVATE("Deactivate organization"),
    ORGANIZATION_DELETE("Delete organization placeholder action"),
    ORGANIZATION_EXPORT("Export organization data"),

    TRAINING_PROVIDER_VIEW("View training providers"),
    TRAINING_PROVIDER_NOTIFY("Send training provider notifications"),
    TRAINING_PROVIDER_RESET_PASSWORD("Reset training provider password"),
    TRAINING_PROVIDER_REMOVE("Remove training provider"),
    TRAINING_PROVIDER_DEACTIVATE("Deactivate training provider"),

    PROGRAMME_VIEW("View programmes"),
    PROGRAMME_APPROVE("Approve programme"),
    PROGRAMME_SUSPEND("Suspend programme"),

    CURRICULUM_VIEW("View curriculum submissions"),
    CURRICULUM_APPROVE("Approve curriculum submission"),
    CURRICULUM_REJECT("Reject curriculum submission"),
    CURRICULUM_REQUEST_REVISION("Request curriculum revision"),

    REPORT_VIEW("View platform reports"),
    REPORT_EXPORT("Export platform reports"),

    FINANCE_VIEW("View finance dashboard and transactions"),
    FINANCE_EXPORT("Export finance transactions"),

    SUPPORT_VIEW("View help articles and tickets"),
    SUPPORT_TICKET_CREATE("Create support tickets"),
    SUPPORT_TICKET_REPLY("Reply to support tickets"),
    SUPPORT_CONTACT_SUBMIT("Submit support contact form"),

    ADMIN_VIEW("View government admins"),
    ADMIN_CREATE("Create government admins"),
    ADMIN_UPDATE("Update government admins"),
    ADMIN_DEACTIVATE("Deactivate government admins"),
    ADMIN_ROLE_ASSIGN("Assign roles to government admins"),

    ROLE_VIEW("View government roles"),
    ROLE_CREATE("Create government roles"),
    ROLE_UPDATE("Update government roles"),
    ROLE_DELETE("Delete government roles");

    private final String description;

    public boolean isReadPermission() {
        String name = name();
        return name.endsWith("_VIEW") || name.endsWith("_EXPORT");
    }
}
