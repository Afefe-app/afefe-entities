package com.ocean.afefe.entities.modules.nse.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "NseTraineeQuizOption")
@Table(name = "trainee_quiz_options", schema = "afefe_nse")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeQuizOption extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private TraineeQuizQuestion question;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String label;

    @Column(nullable = false)
    private boolean correct;
}
