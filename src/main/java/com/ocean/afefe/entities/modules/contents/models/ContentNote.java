package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "content_notes"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentNote extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "object_type", nullable = false, columnDefinition = "VARCHAR")
    @Enumerated(value = EnumType.STRING)
    private ContentNoteObjectType objectType;

    /**
     * Referenced entity id (course, module, lesson, or asset). Persisted as VARCHAR in PostgreSQL
     * so Hibernate must map UUID as string; otherwise comparisons fail with
     * {@code operator does not exist: character varying = uuid}.
     */
    @Column(name = "object_id", nullable = false, length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID objectId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    /** Snapshot of lesson-asset progress when the note was taken (e.g. percent 0–100); nullable. */
    @Column(precision = 5, scale = 2)
    private BigDecimal assetProgress;
}
