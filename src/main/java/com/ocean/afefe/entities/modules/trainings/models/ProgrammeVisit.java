package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "programme_visit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeVisit extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    /** Display title, e.g. "NCDMB — Routine Monitoring" */
    @Column(nullable = false)
    private String title;

    /** Organisation that conducted the visit (e.g. NCDMB, Heritage Oil). */
    private String organisationLabel;

    /** Visit type label (e.g. routine monitoring, sponsor review). */
    private String visitType;

    private LocalDate visitDate;

    private LocalTime visitTime;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private ProgrammeItemStatus status = ProgrammeItemStatus.PENDING;

    /** Free-text outcome / findings summary. */
    @Column(columnDefinition = "TEXT")
    private String outcome;

    @Builder.Default
    private int findingsTotal = 0;

    @Builder.Default
    private int findingsResolved = 0;

    @Builder.Default
    private int findingsOpen = 0;

    /** When true this row represents the scheduled next visit card. */
    @Builder.Default
    private boolean nextVisit = false;
}
