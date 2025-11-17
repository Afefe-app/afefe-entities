package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teams",
       uniqueConstraints = @UniqueConstraint(name = "uk_teams_org_name", columnNames = {"org_id","name"}),
       indexes = {
         @Index(name = "idx_teams_parent_team_id", columnList = "parent_team_id")
       })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Team extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_team_id")
    private Team parentTeam;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Lob
    @Column(name = "description", columnDefinition = "text")
    private String description;
}
