package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "scorm_packages"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScormPackage extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String packageUri;

    private String scormVersion;

    private String launchPath;

    @Column(columnDefinition = "TEXT")
    private String manifestXml;
}
