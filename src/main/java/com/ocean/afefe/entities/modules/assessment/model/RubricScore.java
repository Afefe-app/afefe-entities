package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "rubric_scores",
        indexes = {
                @Index(name = "idx_rubric_id", columnList = "rubric_id"),
                @Index(name = "idx_submission_id", columnList = "submission_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RubricScore extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rubric_id", nullable = false)
    private GradingRubric rubric;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private AssignmentSubmission submission;

    @Column(nullable = false)
    private String criterion;

    @Column( nullable = false)
    private Double maxPoints;

    private Double pointsAwarded;

    @Column(columnDefinition = "TEXT")
    private String comments;

}
