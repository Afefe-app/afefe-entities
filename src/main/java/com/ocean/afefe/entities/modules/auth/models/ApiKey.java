package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "api_keys",
       uniqueConstraints = @UniqueConstraint(name = "uk_api_keys_hashed_key", columnNames = "hashed_key"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApiKey extends BaseUUIDEntity {

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_org_id")
    private Organization ownerOrg;

    @Column(name = "hashed_key", nullable = false, length = 255)
    private String hashedKey;

    @Lob
    @Column(name = "scopes", columnDefinition = "text")
    private String scopes;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "last_used_at")
    private OffsetDateTime lastUsedAt;
}
