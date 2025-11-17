package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "team_members",
       uniqueConstraints = @UniqueConstraint(name = "uk_team_members_team_user", columnNames = {"team_id","user_id"}),
       indexes = {
         @Index(name = "idx_team_members_user_id", columnList = "user_id")
       })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamMember extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "team_role", length = 50)
    private String teamRole;

    @Column(name = "joined_at", nullable = false)
    private OffsetDateTime joinedAt;
}
