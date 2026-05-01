package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "trainee_quiz_attempts",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_trainee_quiz_attempt_enrollment_number", columnNames = {"enrollment_id", "quiz_id", "attempt_number"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeQuizAttempt extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private TrainingEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private TraineeQuiz quiz;

    @Column(nullable = false)
    private Integer attemptNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private TraineeQuizAttemptStatus attemptStatus;

    private Integer scorePercent;

    private Instant startedAt;

    private Instant submittedAt;

    /** JSON: map questionId -> selected optionId */
    @Column(columnDefinition = "TEXT")
    private String answersJson;
}
