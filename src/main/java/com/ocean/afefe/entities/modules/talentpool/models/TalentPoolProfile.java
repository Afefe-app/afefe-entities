package com.ocean.afefe.entities.modules.talentpool.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "talent_pool_profiles",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_talent_pool_profile_user", columnNames = {"user_id"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalentPoolProfile extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String skillsCsv;

    private Integer yearsExperience;

    private String location;

    private String cvUrl;
}
