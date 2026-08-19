package com.ocean.afefe.entities.modules.nse.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "NseOrganization")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "afefe_nse")
public class Organization extends BaseUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
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

    private String contactEmail;

    private String websiteUrl;

    @Column(columnDefinition = "TEXT")
    private String addressLine;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String logoUrl;

    private String primaryColorHex;

    private String secondaryColorHex;

    private String tertiaryColorHex;
}
