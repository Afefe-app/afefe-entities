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
@Table(name = "trainer_proctored_test_candidate_violations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProctoredTestCandidateViolation extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private TrainerProctoredTestCandidate candidate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainerProctoredTestViolationType violationType;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String evidenceImageUrl;

    @Builder.Default
    @Column(nullable = false)
    private boolean flagged = true;

    @Column(nullable = false)
    private Instant occurredAt;
}
