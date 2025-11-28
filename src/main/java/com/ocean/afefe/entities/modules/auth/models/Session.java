package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sessions",
       indexes = {
         @Index(name = "idx_sessions_user_id", columnList = "user_id"),
         @Index(name = "idx_sessions_user_created_at", columnList = "user_id, created_at")
       })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;

    private String ipAddress;

    private String userAgent;

    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;
}
