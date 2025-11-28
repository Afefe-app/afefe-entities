package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "course_prerequisites",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_course_requires",
                        columnNames = {"course_id", "requires_course_id"}
                )
        },
        indexes = {
                @Index(name = "idx_requires_course_id", columnList = "requires_course_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoursePrerequisite extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requires_course_id", nullable = false)
    private Course requiresCourse;

}
