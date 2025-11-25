package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "quiz_items",
        indexes = {
                @Index(name = "idx_quiz_items_quiz_id", columnList = "quiz_id"),
                @Index(name = "idx_quiz_items_question_id", columnList = "question_id"),
                @Index(name = "idx_quiz_items_section_id", columnList = "section_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_quiz_items_quiz_id_position",
                        columnNames = {"quiz_id", "position"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizItem extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private QuizSection section;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal weight;

}
