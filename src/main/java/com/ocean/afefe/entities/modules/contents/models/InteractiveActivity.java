package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interactive_activities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InteractiveActivity extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InteractiveActivityType activityType;

    @Column(nullable = false)
    private String configJson;
}
