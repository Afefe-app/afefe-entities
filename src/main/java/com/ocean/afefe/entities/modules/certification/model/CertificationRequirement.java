package com.ocean.afefe.entities.modules.certification.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.analytics.model.Skill;
import com.ocean.afefe.entities.modules.assessment.model.Quiz;
import com.ocean.afefe.entities.modules.contents.models.Course;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "certification_requirements"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequirement extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    private CertificationTrack track;

    @Column(name = "requirement_type", nullable = false)
    private String requirementType; // course, quiz, skill, external

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private Double minScore;

    private Integer minLevelRank;

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory;

    private Integer position;

}
