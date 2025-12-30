package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "lessons",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_module_position",
                        columnNames = {"module_id", "position"}
                )
        }
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

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(30)")
    private LessonContentType contentType;

    private Integer durationSeconds;

    @Column(nullable = false)
    private int position;

    @Column(nullable = false)
    private Boolean isPublished;
}
