package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthIdentity extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String provider;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sso_provider_id", nullable = false)
    private SSOProvider ssoProvider;

    @Column(nullable = false, unique = true)
    private String providerUserID;

    private String emailAddress;

    private String secretHash;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String accessToken;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String refreshToken;

    private Instant lastLoginAt;
}
