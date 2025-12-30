package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_localizations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseLocalization extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_version_id", nullable = false)
    private CourseVersion courseVersion;

    @Column(nullable = false)
    private String locale;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;
}
