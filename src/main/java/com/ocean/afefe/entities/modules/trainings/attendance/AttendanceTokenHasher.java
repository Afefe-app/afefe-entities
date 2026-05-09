package com.ocean.afefe.entities.modules.trainings.attendance;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.UUID;

/**
 * Deterministic hash for attendance tokens. Instructor/admin APIs must use the same algorithm when persisting
 * {@link com.ocean.afefe.entities.modules.calendar.model.CalendarEvent#getAttendanceTokenHash()}.
 */
public final class AttendanceTokenHasher {

    private AttendanceTokenHasher() {}

    public static String sha256Hex(UUID calendarEventId, String plainToken) {
        if (calendarEventId == null || plainToken == null) {
            return null;
        }
        String normalized = plainToken.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        String payload = calendarEventId + "\0" + normalized;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(payload.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean matches(UUID calendarEventId, String plainToken, String storedHexHash) {
        if (storedHexHash == null || storedHexHash.isEmpty()) {
            return false;
        }
        String computed = sha256Hex(calendarEventId, plainToken);
        if (computed == null) {
            return false;
        }
        return MessageDigest.isEqual(
                storedHexHash.getBytes(StandardCharsets.UTF_8),
                computed.getBytes(StandardCharsets.UTF_8));
    }
}
