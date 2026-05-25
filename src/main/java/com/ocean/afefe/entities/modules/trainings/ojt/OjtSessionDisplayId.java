package com.ocean.afefe.entities.modules.trainings.ojt;

import java.util.Locale;

public final class OjtSessionDisplayId {

    private static final String PREFIX = "STU-";

    private OjtSessionDisplayId() {}

    public static String fromGuid(String guid) {
        if (guid == null || guid.isBlank()) {
            return PREFIX + "00000";
        }
        String digits = guid.replace("-", "");
        String tail = digits.substring(Math.max(0, digits.length() - 5));
        return PREFIX + tail.toUpperCase(Locale.ROOT);
    }
}
