package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.contents.models.Course;
import jakarta.persistence.*;
import lombok.*;

/**
 * Links a published course (from Course / Curriculum catalog) onto a training track.
 */
@Entity
@Table(
        name = "training_track_courses",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_training_track_courses_track_course",
                        columnNames = {"track_id", "course_id"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingTrackCourse extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "track_id", nullable = false)
    private TrainingTrack track;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Builder.Default
    @Column(nullable = false)
    private int position = 0;
}
