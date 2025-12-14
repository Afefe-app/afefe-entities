package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organization extends BaseUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrganizationStatus status;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrgPlanTier planTier;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrganizationRole role;
}
