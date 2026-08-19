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

@Entity(name = "NseTrainingContentBlock")
@Table(name = "training_content_blocks", schema = "afefe_nse")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentBlock extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "content_item_id", nullable = false)
    private TrainingContentItem contentItem;

    @Column(nullable = false)
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private TrainingContentBlockType blockType;

    @Column(columnDefinition = "TEXT")
    private String payloadJson;

    @Column(columnDefinition = "TEXT")
    private String resourceUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainee_quiz_id")
    private TraineeQuiz traineeQuiz;

    @Builder.Default
    private long estimatedDurationSeconds = 0;
}
