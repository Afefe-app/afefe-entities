package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_localizations",
       uniqueConstraints = @UniqueConstraint(name = "uk_course_localizations_cv_locale", columnNames = {"course_version_id","locale"}),
       indexes = @Index(name = "idx_course_localizations_cv_id", columnList = "course_version_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseLocalization extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_version_id", nullable = false)
    private CourseVersion courseVersion;

    @Column(name = "locale", nullable = false, length = 20)
    private String locale;

    @Column(name = "title", length = 255)
    private String title;

    @Lob
    @Column(name = "summary", columnDefinition = "text")
    private String summary;
}
