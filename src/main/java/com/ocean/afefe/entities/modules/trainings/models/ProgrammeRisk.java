package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programme_risk")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeRisk extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    /** Human-readable code e.g. RSK-001 or ISS-001. */
    private String code;

    /** RISK or ISSUE. */
    @Column(length = 20)
    @Builder.Default
    private String kind = "RISK";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private ProgrammeRiskSeverity severity = ProgrammeRiskSeverity.MEDIUM;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String ownerName;

    @Column(columnDefinition = "TEXT")
    private String mitigation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private ProgrammeItemStatus status = ProgrammeItemStatus.OPEN;
}
