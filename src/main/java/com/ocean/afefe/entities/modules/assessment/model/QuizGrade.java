package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "quiz_grades",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_attempt_grade",
                        columnNames = {"attempt_id"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizGrade extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User gradedBy;

    private Instant gradedAt;

    private Double totalPoints;

    @Column(columnDefinition = "TEXT")
    private String comments;
}
