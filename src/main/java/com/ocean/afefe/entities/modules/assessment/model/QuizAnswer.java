package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "quiz_answers",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_attempt_question",
                        columnNames = {"attempt_id", "question_id"}
                )
        },
        indexes = {
                @Index(name = "idx_question_id", columnList = "question_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionOption selectedOption;

    @Column(columnDefinition = "TEXT")
    private String answerText;

    private Boolean isCorrect;

    private Double pointsAwarded;

    @Column( nullable = false)
    private Instant answeredAt;
}
