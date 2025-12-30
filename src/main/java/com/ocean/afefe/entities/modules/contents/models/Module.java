package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "modules"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Module extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_version_id", nullable = false)
    private CourseVersion courseVersion;

    @Column(nullable = false)
    private String title;

    private Integer position;

    @Column(columnDefinition = "TEXT")
    private String summary;

}
