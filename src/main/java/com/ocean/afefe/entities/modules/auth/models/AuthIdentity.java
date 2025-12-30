package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Getter
@Setter
@Entity
@Builder
@Table(
        name = "auth_identities"
)
@NoArgsConstructor
@AllArgsConstructor
public class AuthIdentity extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String provider;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SSOProvider ssoProvider;

    @Column(nullable = false)
    private String providerUserID;

    private String emailAddress;

    private String secretHash;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String accessToken;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String refreshToken;

    private Instant lastLoginAt;
}
