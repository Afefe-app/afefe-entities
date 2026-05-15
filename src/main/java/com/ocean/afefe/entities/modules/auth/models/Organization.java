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

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private OrganizationStatus status;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrgPlanTier planTier;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrganizationRole role;

    @Column(length = 500)
    private String website;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 500)
    private String description;

    @Column(length = 1000)
    private String logoUrl;

    @Column(length = 16)
    private String primaryColor;

    @Column(length = 16)
    private String secondaryColor;

    @Column(length = 16)
    private String tertiaryColor;
}
