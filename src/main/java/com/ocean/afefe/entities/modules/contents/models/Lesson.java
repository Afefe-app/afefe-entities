package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "lessons"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private LessonContentType contentType;

    private Integer durationSeconds;

    @Column(nullable = false)
    private int position;

    private Boolean isPublished;

    /** True when at least one quiz exists for this lesson. Maintained by application code. */
    @Builder.Default
    @Column(nullable = false)
    private boolean hasQuiz = false;
}
