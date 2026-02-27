package com.ocean.afefe.entities.modules.enrollments.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.LessonAsset;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "lesson_progress"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonProgress extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_asset_id", nullable = false)
    private LessonAsset lessonAsset;

    @Column(nullable = false)
    private String status; // NOT_STARTED, IN_PROGRESS, COMPLETED

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @Column(nullable = false)
    private Integer lastPositionSeconds;

    private Instant lastViewedAt;

    private Instant completedAt;
}
