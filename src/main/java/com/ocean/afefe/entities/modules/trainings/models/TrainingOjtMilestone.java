package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private Integer offsetDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "milestone_type", nullable = false, columnDefinition = "VARCHAR(40)")
    private TrainingOjtMilestoneType milestoneType;
}
