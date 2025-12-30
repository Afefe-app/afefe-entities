package com.ocean.afefe.entities.modules.analytics.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "skill_levels",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_skill_rank",
                        columnNames = {"skill_id", "level_rank"}
                ),
                @UniqueConstraint(
                        name = "uk_skill_level_name",
                        columnNames = {"skill_id", "level_name"}
                )
        },
        indexes = {
                @Index(name = "idx_skill_id", columnList = "skill_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillLevel extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "level_rank", nullable = false)
    private Integer levelRank;

    @Column(name = "level_name", nullable = false)
    private String levelName;

    @Column(columnDefinition = "TEXT")
    private String rubricJson;

}
