package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.calendar.model.CalendarEvent;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "training_session_attendance",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_training_session_attendance_enrollment_event",
                        columnNames = {"training_enrollment_id", "calendar_event_id"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionAttendance extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_enrollment_id", nullable = false)
    private TrainingEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calendar_event_id", nullable = false)
    private CalendarEvent calendarEvent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private TrainingSessionAttendanceStatus status;

    @Column(nullable = false)
    private Instant markedAt;

    @Builder.Default
    @Column(nullable = false)
    private boolean liveLocationUsed = false;

    private BigDecimal submittedLatitude;

    private BigDecimal submittedLongitude;
}
