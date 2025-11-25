package com.ocean.afefe.entities.modules.enrollments.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "reminders",
        indexes = {
                @Index(name = "idx_reminders_org_id", columnList = "org_id"),
                @Index(name = "idx_reminders_user_id", columnList = "user_id"),
                @Index(name = "idx_reminders_enrollment_id", columnList = "enrollment_id"),
                @Index(name = "idx_reminders_scheduled_at", columnList = "scheduled_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reminder extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @Column(nullable = false)
    private String channel; // in_app, email, push

    @Column(name = "scheduled_at", nullable = false)
    private Instant scheduledAt;

    private Instant sentAt;

    private String payloadJson;

}
