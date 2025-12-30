package com.ocean.afefe.entities.modules.certification.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "certification_expiries"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationExpiry extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id", nullable = false)
    private Certificate certificate;

    @Column( nullable = false)
    private Instant notifyAt;

    @Column(nullable = false)
    private String notificationStatus;  // pending, sent, failed
}
