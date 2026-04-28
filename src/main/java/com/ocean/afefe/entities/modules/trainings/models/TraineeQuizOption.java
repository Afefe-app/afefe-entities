package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainee_quiz_options")
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
