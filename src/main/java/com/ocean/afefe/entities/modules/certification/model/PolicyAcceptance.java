package com.ocean.afefe.entities.modules.certification.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "policy_acceptances",
        indexes = {
                @Index(name = "idx_org_user_policy_version", columnList = "org_id, user_id, policy_key, policy_version", unique = true),
                @Index(name = "idx_org_id", columnList = "org_id"),
                @Index(name = "idx_user_id", columnList = "user_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyAcceptance extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "policy_key", nullable = false)
    private String policyKey;

    @Column(name = "policy_version", nullable = false)
    private String policyVersion;

    @Column( nullable = false, updatable = false)
    private Instant acceptedAt;

    private String ipAddress;

    private String userAgent;
}
