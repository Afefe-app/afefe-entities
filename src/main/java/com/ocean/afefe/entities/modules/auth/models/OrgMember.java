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
        name = "org_members"
)
@AllArgsConstructor
@NoArgsConstructor
public class OrgMember extends BaseUUIDEntity {

    @ManyToOne
    private Organization organization;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status invitationStatus;

    private Instant invitedAt;

    private Instant joinedAt;
}
