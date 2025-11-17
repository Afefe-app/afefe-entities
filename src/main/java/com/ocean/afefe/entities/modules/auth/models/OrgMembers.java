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
        name = "org_members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_org",
                        columnNames = {"org_id"}
                )
        }
)
@AllArgsConstructor
@NoArgsConstructor
public class OrgMembers extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Org org;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String invitationStatus;

    private Instant invitedAt;

    private Instant joinedAt;
}
