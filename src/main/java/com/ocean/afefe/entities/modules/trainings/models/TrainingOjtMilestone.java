package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "training_ojt_milestone")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingOjtMilestone extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Builder.Default
    private Integer offsetDays = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "milestone_type", nullable = false, columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private TrainingOjtMilestoneType milestoneType = TrainingOjtMilestoneType.WEEK;

    /** Programme Management V2 — absolute due date for the milestone. */
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private TrainingMilestoneStatus status = TrainingMilestoneStatus.UPCOMING;

    /** 0–100 progress toward the milestone (e.g. Technical Course Phase at 72%). */
    @Builder.Default
    private Integer progressPercent = 0;

    private Instant completedAt;
}
