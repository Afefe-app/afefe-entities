package com.ocean.afefe.entities.modules.enrollments.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.LessonAsset;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "resume_points"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumePoint extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_asset_id", nullable = false)
    private LessonAsset lessonAsset;

    @Column(nullable = false)
    private Integer positionSeconds;

}
