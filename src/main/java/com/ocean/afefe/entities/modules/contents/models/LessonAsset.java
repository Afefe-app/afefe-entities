package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "lesson_assets"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonAsset extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LessonAssetType assetType;

    @Column(nullable = false)
    private String uri;

    private String checksum;

    private Long fileSizeBytes;

    private String mimeType;
}
