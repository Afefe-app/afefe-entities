package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
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
@Table(name = "trainer_proctored_test_candidates")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProctoredTestCandidate extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proctored_test_id", nullable = false)
    private TrainerProctoredTest proctoredTest;

    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String candidateCode;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private TrainerProctoredTestCandidateStatus inviteStatus = TrainerProctoredTestCandidateStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private TrainerProctoredTestCandidateTestStatus testStatus = TrainerProctoredTestCandidateTestStatus.IN_PROGRESS;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private TrainerProctoredTestCandidateRemark testRemark = TrainerProctoredTestCandidateRemark.NIL;

    @Builder.Default
    @Column(nullable = false)
    private boolean shortlisted = false;

    private Integer totalQuestions;

    private Integer questionsAnswered;

    private Integer testScorePercent;

    private Integer tabSwitchCount;

    private Integer noiseIncidentCount;

    private Integer multipleFaceCount;

    private Integer flaggedIncidentCount;

    private Instant candidateStartAt;

    private Instant candidateEndAt;

    private Instant invitedAt;

    private Instant startedAt;

    private Instant completedAt;

    private Long durationSeconds;

    private String videoUrl;

    private String videoThumbnailUrl;

    @Builder.Default
    @Column(nullable = false)
    private boolean liveSession = false;
}
