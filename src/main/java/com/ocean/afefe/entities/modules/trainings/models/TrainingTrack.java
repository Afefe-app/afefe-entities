package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * A delivery cohort (batch) under a published programme ({@link Training}).
 * Training Management organises trainees, trainers and courses per track.
 */
@Entity
@Table(
        name = "training_tracks",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_training_tracks_training_name",
                        columnNames = {"training_id", "name"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingTrack extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    /** Display name, e.g. "Track A - Technical batch". */
    @Column(nullable = false)
    private String name;

    /** Short code shown in trainee tables (A, B, C…). */
    @Column(length = 32)
    private String code;

    private String coverImageUrl;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    @Builder.Default
    private TrainingTrackStatus status = TrainingTrackStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
