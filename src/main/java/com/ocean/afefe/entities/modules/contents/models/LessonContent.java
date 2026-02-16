package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonContent extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private LessonContentType contentType;

    @Builder.Default
    private Integer courseVersion = 1;

}
