package com.ocean.afefe.entities.modules.analytics.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "skill_relations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_parent_child_skill",
                        columnNames = {"parent_skill_id", "child_skill_id"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillRelation extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_skill_id", nullable = false)
    private Skill parentSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_skill_id", nullable = false)
    private Skill childSkill;

    @Column( nullable = false)
    private String relationType; // prerequisite, broader, narrower

}
