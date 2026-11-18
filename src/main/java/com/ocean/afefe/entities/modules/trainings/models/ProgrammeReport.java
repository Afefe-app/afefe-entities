package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "programme_report")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeReport extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    /** e.g. RPT-2026-007 */
    private String reportCode;

    @Column(nullable = false)
    private String title;

    private LocalDate periodFrom;

    private LocalDate periodTo;

    private String trackLabel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private ProgrammeItemStatus status = ProgrammeItemStatus.PENDING;

    private String documentUrl;

    @Builder.Default
    private int preparedPercent = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by_id")
    private User generatedBy;
}
