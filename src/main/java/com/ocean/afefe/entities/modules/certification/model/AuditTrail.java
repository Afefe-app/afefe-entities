package com.ocean.afefe.entities.modules.certification.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "audit_trails",
        indexes = {
                @Index(name = "idx_org_id", columnList = "org_id"),
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_action", columnList = "action"),
                @Index(name = "idx_object_type", columnList = "object_type"),
                @Index(name = "idx_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditTrail extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String action;

    @Column(name = "object_type")
    private String objectType;

    private String objectId;

    private String ipAddress;

    private String userAgent;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

}
