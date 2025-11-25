package com.ocean.afefe.entities.modules.assessment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "question_metadata",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_metadata_question_key",
                        columnNames = {"question_id", "`key`"}
                )
        },
        indexes = {
                @Index(name = "idx_question_metadata_question_id", columnList = "question_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionMetadata extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "key", nullable = false)
    private String key;

    private String value;
}
