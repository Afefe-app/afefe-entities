package com.ocean.afefe.entities.modules.enrollments.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.Module;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "module_progress",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_module_progress_enrollment_module",
                        columnNames = {"enrollment_id", "module_id"}
                )
        },
        indexes = {
                @Index(name = "idx_module_progress_module_id", columnList = "module_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleProgress extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(nullable = false)
    private String status;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    private Instant startedAt;

    private Instant completedAt;

}
