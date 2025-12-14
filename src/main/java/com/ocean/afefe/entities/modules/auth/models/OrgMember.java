package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.common.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Getter
@Setter
@Entity
@Builder
@Table(
        name = "org_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_org_member_user_org",
                        columnNames = {"user_id", "organization_id"}
                )
        },
        indexes = {
                @Index(name = "idx_org_member_organization_id", columnList = "organization_id"),
                @Index(name = "idx_org_member_user_id", columnList = "user_id")
        }
)
@AllArgsConstructor
@NoArgsConstructor
public class OrgMember extends BaseUUIDEntity {

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status invitationStatus;

    private Instant invitedAt;

    private Instant joinedAt;
}
