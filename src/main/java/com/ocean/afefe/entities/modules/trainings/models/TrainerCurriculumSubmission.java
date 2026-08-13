package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "trainer_curriculum_submissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerCurriculumSubmission extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(nullable = false)
    private String curriculumCode;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String programmeId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String durationLabel;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String targetAudience;

    @Column(columnDefinition = "TEXT")
    private String prerequisitesJson;

    @Column(columnDefinition = "TEXT")
    private String learningObjectivesJson;

    @Column(columnDefinition = "TEXT")
    private String relatedCategoriesJson;

    @Column(columnDefinition = "TEXT")
    private String monthWeekStructureJson;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String documentUrl;

    private String documentName;

    private Long documentSizeBytes;

    private String documentMimeType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainerCurriculumStatus status;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Builder.Default
    private Integer complianceScore = 0;

    @Column(columnDefinition = "TEXT")
    private String complianceChecklistJson;

    private String trainingMode;

    private Integer minOjtHours;

    @Column(columnDefinition = "TEXT")
    private String assessmentMethodsJson;

    @Column(nullable = false)
    private Instant submittedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Override
    public void prePersist() {
        super.prePersist();
        if (submittedAt == null) {
            submittedAt = Instant.now();
        }
        if (complianceScore == null) {
            complianceScore = 0;
        }
    }
}
