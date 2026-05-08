package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainee_quiz_questions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeQuizQuestion extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private TraineeQuiz quiz;

    @Column(nullable = false)
    private Integer position;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TraineeQuizQuestionType questionType = TraineeQuizQuestionType.MULTI_CHOICE;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt;
}
