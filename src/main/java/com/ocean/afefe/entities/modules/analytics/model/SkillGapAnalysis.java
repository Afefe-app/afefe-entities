package com.ocean.afefe.entities.modules.analytics.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "skill_gap_analyses",
        indexes = {
                @Index(name = "idx_skill_gap_analyses_org_id", columnList = "org_id"),
                @Index(name = "idx_skill_gap_analyses_user_id", columnList = "user_id"),
                @Index(name = "idx_skill_gap_analyses_job_role_profile_id", columnList = "job_role_profile_id"),
                @Index(name = "idx_skill_gap_analyses_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillGapAnalysis extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_role_profile_id")
    private JobRoleProfile jobRoleProfile;

    @Column(columnDefinition = "TEXT")
    private String targetSnapshotJson; // frozen skill requirements at analysis time

    private Double gapScore;

}
