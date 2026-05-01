package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "training_content_block_progress",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_training_block_progress", columnNames = {"enrollment_id", "block_id"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingContentBlockProgress extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private TrainingEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "block_id", nullable = false)
    private TrainingContentBlock block;

    @Column(nullable = false)
    private Integer lastPositionSeconds;

    private Instant lastViewedAt;

    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private TrainingContentProgressStatus progressStatus;
}
