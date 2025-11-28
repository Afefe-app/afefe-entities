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
        name = "compliance_status",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_org_user_standard", columnNames = {"org_id", "user_id", "standard_id"})
        },
        indexes = {
                @Index(name = "idx_org_id", columnList = "org_id"),
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_standard_id", columnList = "standard_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceStatus extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "standard_id")
    private ComplianceStandard standard;

    @Column( nullable = false)
    private String overallStatus; // compliant, partial, non_compliant, unknown

    @Column(columnDefinition = "TEXT")
    private String metricsJson;

    @Column( nullable = false)
    private Instant lastCheckedAt;
}
