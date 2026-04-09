package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "super_admins",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
public class SuperAdmin extends BaseUUIDEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR")
    @Builder.Default
    private SuperAdminStatus status = SuperAdminStatus.ACTIVE;

    @Column(nullable = false)
    private UUID grantedBy;

    @Column(nullable = false)
    private Instant grantedAt;

    private Instant revokedAt;
}
