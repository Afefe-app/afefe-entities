package com.ocean.afefe.entities.modules.certification.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "compliance_standards",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_org_code_version", columnNames = {"org_id", "code", "version"})
        },
        indexes = {
                @Index(name = "idx_org_id", columnList = "org_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceStandard extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String version;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean isActive;
}
