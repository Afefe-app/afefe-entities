package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oauth_clients",
       indexes = @Index(name = "idx_oauth_clients_org_id", columnList = "org_id"),
       uniqueConstraints = @UniqueConstraint(name = "uk_oauth_clients_client_id", columnNames = "client_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OauthClient extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "client_id", nullable = false, length = 255)
    private String clientId;

    @Column(name = "client_secret_hash", nullable = false, length = 255)
    private String clientSecretHash;

    @Column(name = "grant_types", length = 255)
    private String grantTypes;

    @Lob
    @Column(name = "scopes", columnDefinition = "text")
    private String scopes;

    @Lob
    @Column(name = "redirect_uris", columnDefinition = "text")
    private String redirectUris;
}
