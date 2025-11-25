package com.ocean.afefe.entities.modules.certification.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "compliance_assignments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_requirement_user", columnNames = {"requirement_id", "user_id"})
        },
        indexes = {
                @Index(name = "idx_org_id", columnList = "org_id"),
                @Index(name = "idx_requirement_id", columnList = "requirement_id"),
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_status", columnList = "status"),
                @Index(name = "idx_due_at", columnList = "due_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceAssignment extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id", nullable = false)
    private ComplianceRequirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Instant dueAt;

    @Column(nullable = false)
    private String status; // assigned, in_progress, satisfied, overdue, waived

    private Instant satisfiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Certificate satisfiedByCertificate;

    @Column( nullable = false)
    private Integer attemptsCount;

    private Instant lastAttemptAt;
}
