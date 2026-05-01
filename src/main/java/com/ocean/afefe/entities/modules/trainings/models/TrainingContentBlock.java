package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "training_content_blocks")
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

    /** JSON payload: rich text, URLs, captions, etc. */
    @Column(columnDefinition = "TEXT")
    private String payloadJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainee_quiz_id")
    private TraineeQuiz traineeQuiz;

    @Builder.Default
    private long estimatedDurationSeconds = 0;
}
