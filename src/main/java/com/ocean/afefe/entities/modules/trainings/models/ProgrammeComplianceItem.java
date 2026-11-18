package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programme_compliance_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeComplianceItem extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private String title;

    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private ProgrammeItemStatus status = ProgrammeItemStatus.PENDING;
}
