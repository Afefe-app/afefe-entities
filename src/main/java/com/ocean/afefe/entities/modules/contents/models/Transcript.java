package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "transcripts"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transcript extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String locale;

    @Column(columnDefinition = "TEXT")
    private String content;

}
