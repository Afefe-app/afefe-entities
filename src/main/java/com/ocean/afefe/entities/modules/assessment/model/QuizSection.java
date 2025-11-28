package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "quiz_sections",
        indexes = {
                @Index(name = "idx_quiz_sections_quiz_id", columnList = "quiz_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_quiz_sections_quiz_id_position", columnNames = {"quiz_id", "position"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSection extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(nullable = false)
    private Integer position;
}
