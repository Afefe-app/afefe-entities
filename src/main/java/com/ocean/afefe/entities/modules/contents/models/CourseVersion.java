package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "course_versions",
       uniqueConstraints = @UniqueConstraint(name = "uk_course_versions_course_version", columnNames = {"course_id","version_num"}),
       indexes = @Index(name = "idx_course_versions_course_id", columnList = "course_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseVersion extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "version_num", nullable = false)
    private Integer versionNum;

    @Lob
    @Column(name = "changelog", columnDefinition = "text")
    private String changelog;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "draft";

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}
