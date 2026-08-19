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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "NseTraineeQuizQuestion")
@Table(name = "trainee_quiz_questions", schema = "afefe_nse")
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
    @Builder.Default
    private TraineeQuizQuestionType questionType = TraineeQuizQuestionType.MULTI_CHOICE;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String prompt;

    private Integer points;
}
