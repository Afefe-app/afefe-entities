package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "training_content_item_progress",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_training_item_progress", columnNames = {"enrollment_id", "content_item_id"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentItemProgress extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private TrainingEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "content_item_id", nullable = false)
    private TrainingContentItem contentItem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private TrainingContentProgressStatus progressStatus;

    @Column(nullable = false)
    private Integer progressPercent;

    private Instant lastAccessedAt;

    @Column(name = "current_block_id")
    private UUID currentBlockId;
}
