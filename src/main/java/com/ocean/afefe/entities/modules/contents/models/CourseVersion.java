package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Table(name = "course_versions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseVersion extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer versionNum;

    @Column(columnDefinition = "TEXT")
    private String changelog;

    @Column(nullable = false)
    private String status;

    private Instant publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
}
