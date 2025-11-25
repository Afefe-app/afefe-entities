package com.ocean.afefe.entities.modules.enrollments.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "deadlines",
        indexes = {
                @Index(name = "idx_deadlines_enrollment_id", columnList = "enrollment_id"),
                @Index(name = "idx_deadlines_due_at", columnList = "due_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deadline extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Column(name = "due_at", nullable = false)
    private Instant dueAt;

    @Column(nullable = false)
    private String kind; // course, module, lesson, certification

}
