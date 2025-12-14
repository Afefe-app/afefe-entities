package com.ocean.afefe.entities.modules.certification.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "certificate_verifications",
        indexes = {
                @Index(name = "idx_verified_at", columnList = "verified_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateVerification extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;

    @Column(name = "verified_at", nullable = false)
    private Instant verifiedAt;

    private String verifierIp;

    private String verifierUserAgent;

    private String method;  // public_link, api, admin_portal

    private Boolean result;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
