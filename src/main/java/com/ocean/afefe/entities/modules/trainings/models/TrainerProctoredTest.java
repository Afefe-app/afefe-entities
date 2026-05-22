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
@Table(name = "trainer_proctored_tests")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProctoredTest extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(nullable = false)
    private String programmeId;

    @Column(nullable = false)
    private String testName;

    @Column(nullable = false)
    private String testType;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer testDurationMinutes;

    private Instant scheduleStartAt;

    private Instant scheduleEndAt;

    @Builder.Default
    @Column(nullable = false)
    private String scheduleTimezone = "WAT";

    private Integer timeLimitMinutes;

    private Integer passingScorePercent;

    private Integer maxAttempts;

    @Builder.Default
    @Column(nullable = false)
    private boolean unlimitedAttempts = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean randomizeQuestionOrder = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean randomizeAnswerOptions = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean allowBackwardNavigation = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean autoSubmitOnTimeExpiry = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean showResultsImmediately = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean requireWebcam = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean enableScreenRecording = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean enableAudioMonitoring = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean aiBehaviorMonitoring = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean multipleFaceDetection = true;

    @Builder.Default
    @Column(nullable = false)
    private boolean tabSwitchDetection = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainerProctoredTestStatus status;

    private Instant publishedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
