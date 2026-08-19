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

@Entity(name = "NseTrainingWeek")
@Table(name = "training_weeks", schema = "afefe_nse")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingWeek extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "month_id", nullable = false)
    private TrainingMonth month;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    @Column(nullable = false)
    private boolean coAuthoringEnabled = false;
}
