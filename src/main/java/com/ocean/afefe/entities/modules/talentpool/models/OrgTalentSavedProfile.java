package com.ocean.afefe.entities.modules.talentpool.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "org_talent_saved_profiles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_org_talent_saved_profile", columnNames = {"org_id", "talent_user_id"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgTalentSavedProfile extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "talent_user_id", nullable = false)
    private User talentUser;
}
