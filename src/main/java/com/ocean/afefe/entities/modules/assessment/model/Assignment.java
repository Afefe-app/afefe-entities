package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.Module;
import com.ocean.afefe.entities.modules.contents.models.Lesson;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "assignments",
        indexes = {
                @Index(name = "idx_org_id", columnList = "org_id"),
                @Index(name = "idx_course_id", columnList = "course_id"),
                @Index(name = "idx_module_id", columnList = "module_id"),
                @Index(name = "idx_lesson_id", columnList = "lesson_id")
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assignment extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    private Instant dueAt;

    @Column( nullable = false)
    private Double maxPoints;

    @Column(nullable = false)
    private String visibility; // published, draft, hidden, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
