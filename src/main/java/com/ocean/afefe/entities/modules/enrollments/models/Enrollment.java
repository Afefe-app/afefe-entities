package com.ocean.afefe.entities.modules.enrollments.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.Module;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "enrollments"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  Enrollment extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Module currentModule;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    @Enumerated(value = EnumType.STRING)
    private EnrollmentStatus status; // enrolled, in_progress, completed, withdrawn

    @Column( nullable = false)
    private Integer progressPercent;

    private Instant startedAt;

    private Instant completedAt;
}
