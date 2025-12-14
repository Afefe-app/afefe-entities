package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "assignment_submissions",
        indexes = {
                @Index(name = "idx_assignment_submissions_assignment_id", columnList = "assignment_id"),
                @Index(name = "idx_assignment_submissions_enrollment_id", columnList = "enrollment_id"),
                @Index(name = "idx_assignment_submissions_user_id", columnList = "user_id"),
                @Index(name = "idx_assignment_submissions_status", columnList = "status")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentSubmission extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column( nullable = false)
    private Instant submittedAt;

    private String contentUri;

    @Column( columnDefinition = "TEXT")
    private String answerText;

    @Column(nullable = false)
    private String status; // submitted, graded, pending, etc.

    private Instant gradedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graded_by")
    private User gradedBy;

    private Double score;

    @Column(columnDefinition = "TEXT")
    private String feedback;
}
