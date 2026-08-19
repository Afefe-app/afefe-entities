package com.ocean.afefe.entities.modules.nse.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity(name = "NseTraineeQuizAttempt")
@Table(
        name = "trainee_quiz_attempts",
        schema = "afefe_nse",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_nse_trainee_quiz_attempt_enrollment_number",
                        columnNames = {"enrollment_id", "quiz_id", "attempt_number"})
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

    @Column(columnDefinition = "TEXT")
    private String answersJson;
}
