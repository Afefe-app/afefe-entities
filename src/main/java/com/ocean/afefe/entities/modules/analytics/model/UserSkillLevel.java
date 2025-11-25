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
        name = "user_skill_levels",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_skill_observed",
                        columnNames = {"user_id", "skill_id", "observed_at"}
                )
        },
        indexes = {
                @Index(name = "idx_user_skill", columnList = "user_id, skill_id"),
                @Index(name = "idx_is_latest", columnList = "is_latest")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillLevel extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    private Integer levelRank;

    private String levelName;

    private Double computedScore;

    @Column(name = "is_latest", nullable = false)
    private Boolean isLatest;

    @Column(name = "observed_at", nullable = false)
    private Instant observedAt;

}
