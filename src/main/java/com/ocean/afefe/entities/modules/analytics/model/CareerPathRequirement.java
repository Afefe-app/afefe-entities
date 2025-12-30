package com.ocean.afefe.entities.modules.analytics.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.Course;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "career_path_requirements"
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
