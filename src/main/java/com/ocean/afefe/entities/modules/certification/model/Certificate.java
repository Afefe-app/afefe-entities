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
        name = "certificates",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_org_certificate_number",
                        columnNames = {"org_id", "certificate_number"}
                )
        },
        indexes = {
                @Index(name = "idx_org_id", columnList = "org_id"),
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_track_id", columnList = "track_id"),
                @Index(name = "idx_status", columnList = "status")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "track_id", nullable = false)
    private CertificationTrack track;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private CertificateTemplate template;

    @Column(name = "certificate_number", nullable = false)
    private String certificateNumber;

    @Column(nullable = false)
    private String status;  // active, expired, revoked

    @Column(name = "issued_at", nullable = false)
    private Instant issuedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    private String revocationReason;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;
}
