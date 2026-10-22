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
                @UniqueConstraint(name = "uk_org_member_membership_id", columnNames = {"organization_id", "membership_id"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
public class OrgMember extends BaseUUIDEntity {

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private User user;

    /**
     * External/org membership identifier. When not supplied on create, the platform generates one.
     */
    @Column(name = "membership_id")
    private String membershipId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status invitationStatus;

    private Instant invitedAt;

    private Instant joinedAt;
}
