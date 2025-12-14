package com.ocean.afefe.entities.modules.analytics.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.Course;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "career_path_requirements",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_career_path_skill_course",
                        columnNames = {"career_path_id", "skill_id", "course_id"}
                )
        },
        indexes = {
                @Index(name = "idx_career_path_requirements_career_path_id", columnList = "career_path_id"),
                @Index(name = "idx_career_path_requirements_skill_id", columnList = "skill_id"),
                @Index(name = "idx_career_path_requirements_course_id", columnList = "course_id"),
                @Index(name = "idx_career_path_requirements_position", columnList = "position")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CareerPathRequirement extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_path_id", nullable = false)
    private CareerPath careerPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private Integer requiredLevelRank;

    @Column( nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Boolean isMandatory;

    private Integer position;
}
