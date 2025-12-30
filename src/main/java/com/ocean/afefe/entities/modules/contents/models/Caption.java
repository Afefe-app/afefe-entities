package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "captions"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Caption extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String locale;

    private String vttUri;

    private String srtUri;
}
