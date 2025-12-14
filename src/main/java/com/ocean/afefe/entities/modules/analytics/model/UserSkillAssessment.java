package com.ocean.afefe.entities.modules.analytics.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_skill_assessments",
        indexes = {
                @Index(name = "idx_user_skill_assessments_user_id", columnList = "user_id"),
                @Index(name = "idx_user_skill_assessments_skill_id", columnList = "skill_id"),
                @Index(name = "idx_user_skill_assessments_assessed_at", columnList = "user_id, skill_id, assessed_at"),
                @Index(name = "idx_user_skill_assessments_source", columnList = "source")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillAssessment extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false)
    private String source; // self, quiz, assignment, ai, instructor

    private Double score;

    private Double confidence;

    @Column( nullable = false)
    private Instant assessedAt;

    @Column(columnDefinition = "TEXT")
    private String payloadJson;
}
