package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "courses",
       indexes = {
           @Index(name = "idx_courses_org_id", columnList = "org_id"),
           @Index(name = "idx_courses_owner_instructor_id", columnList = "owner_instructor_id")
       },
       uniqueConstraints = @UniqueConstraint(name = "uk_courses_slug", columnNames = "slug"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Course extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_instructor_id")
    private Instructor ownerInstructor;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "slug", nullable = false, length = 200, unique = true)
    private String slug;

    @Lob
    @Column(name = "summary", columnDefinition = "text")
    private String summary;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "language", length = 20)
    private String language;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "draft";

    @Column(name = "estimated_minutes")
    private Integer estimatedMinutes;

    @Column(name = "price_cents")
    private Integer priceCents;

    @Column(name = "is_free", nullable = false)
    private boolean free = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
