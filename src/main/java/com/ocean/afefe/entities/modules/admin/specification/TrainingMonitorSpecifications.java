package com.ocean.afefe.entities.modules.admin.specification;

import com.ocean.afefe.entities.modules.calendar.model.CalendarEvent;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * JPA {@link Specification} helpers for the superadmin training monitor, which lists
 * {@link CalendarEvent}s attached to trainings within an organization and classifies them
 * as upcoming, ongoing, or past based on the event date and time window.
 */
public final class TrainingMonitorSpecifications {

    private TrainingMonitorSpecifications() {
    }

    /** Only calendar events attached to a training belonging to the given organization. */
    public static Specification<CalendarEvent> forTrainingOrg(UUID orgId) {
        return (root, query, cb) -> {
            var hasTraining = cb.isNotNull(root.get("assignedTraining"));
            if (orgId == null) {
                return hasTraining;
            }
            return cb.and(hasTraining, cb.equal(root.get("assignedTraining").get("org").get("id"), orgId));
        };
    }

    /** Event starts strictly after "now". */
    public static Specification<CalendarEvent> upcoming(LocalDate today, LocalTime now) {
        return (root, query, cb) -> cb.or(
                cb.greaterThan(root.<LocalDate>get("date"), today),
                cb.and(
                        cb.equal(root.<LocalDate>get("date"), today),
                        cb.greaterThan(root.<LocalTime>get("fromTime"), now)
                )
        );
    }

    /** Now falls within the event's [fromTime, toTime] window on the event date. */
    public static Specification<CalendarEvent> ongoing(LocalDate today, LocalTime now) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.<LocalDate>get("date"), today),
                cb.lessThanOrEqualTo(root.<LocalTime>get("fromTime"), now),
                cb.greaterThanOrEqualTo(root.<LocalTime>get("toTime"), now)
        );
    }

    /** Event ended before "now". */
    public static Specification<CalendarEvent> past(LocalDate today, LocalTime now) {
        return (root, query, cb) -> cb.or(
                cb.lessThan(root.<LocalDate>get("date"), today),
                cb.and(
                        cb.equal(root.<LocalDate>get("date"), today),
                        cb.lessThan(root.<LocalTime>get("toTime"), now)
                )
        );
    }
}
