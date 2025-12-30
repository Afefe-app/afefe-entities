package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oauth_clients",
       uniqueConstraints = @UniqueConstraint(name = "uk_oauth_clients_client_id", columnNames = "client_id")
        )
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OauthClient extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @Column( nullable = false)
    private String name;

    @Column(nullable = false)
    private String clientId;

    @Column(nullable = false)
    private String clientSecretHash;

    private String grantTypes;

    @Column(columnDefinition = "TEXT")
    private String scopes;

    @Column( columnDefinition = "TEXT")
    private String redirectUris;
}
