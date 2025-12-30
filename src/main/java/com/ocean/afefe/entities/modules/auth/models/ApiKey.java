package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "api_keys"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey extends BaseUUIDEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User ownerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Organization ownerOrg;

    @Column(nullable = false)
    private String hashedKey;

    @Column(columnDefinition = "TEXT")
    private String scopes;

    private Instant expiresAt;

    private Instant lastUsedAt;
}
