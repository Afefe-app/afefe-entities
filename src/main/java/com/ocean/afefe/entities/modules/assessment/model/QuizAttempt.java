package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant startedAt;

    private Instant submittedAt;

    private Integer durationSeconds;

    private Double scoreObtained;

    private Double scoreMax;

    @Column(nullable = false)
    private String status; // in_progress, submitted, graded, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    private ProctoringSession proctoringSession;

}
