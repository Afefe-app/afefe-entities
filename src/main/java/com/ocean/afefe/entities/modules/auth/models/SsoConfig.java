package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sso_configs",
       uniqueConstraints = @UniqueConstraint(name = "uk_sso_configs_org_provider", columnNames = {"org_id","provider_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SsoConfig extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private SSOProvider provider;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "domain_filter", length = 255)
    private String domainFilter;

    @Column(name = "issuer", length = 500)
    private String issuer;

    @Column(name = "audience", length = 500)
    private String audience;

    @Column(name = "metadata_url", length = 500)
    private String metadataUrl;

    @Column(name = "client_id", length = 255)
    private String clientId;

    @Column(name = "client_secret_hash", length = 255)
    private String clientSecretHash;

    @Column(name = "redirect_uri", length = 500)
    private String redirectUri;
}
