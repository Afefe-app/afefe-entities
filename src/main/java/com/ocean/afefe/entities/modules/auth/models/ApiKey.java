package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "api_keys",
       uniqueConstraints = @UniqueConstraint(
               name = "uk_api_keys_hashed_key",
               columnNames = "hashed_key"
       ),
       indexes = {
               @Index(name = "idx_api_keys_owner_user_id", columnList = "owner_user_id"),
               @Index(name = "idx_api_keys_owner_org_id", columnList = "owner_org_id")
       })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey extends BaseUUIDEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id", nullable = true)
    private User ownerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_org_id", nullable = true)
    private Organization ownerOrg;

    @Column(nullable = false)
    private String hashedKey;

    @Column(columnDefinition = "TEXT")
    private String scopes;

    private Instant expiresAt;

    private Instant lastUsedAt;
}
