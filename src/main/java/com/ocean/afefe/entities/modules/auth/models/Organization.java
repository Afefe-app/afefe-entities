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
    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrgPlanTier planTier;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrganizationRole role;

    /** Optional public contact shown in government lists; falls back to first joined OrgMember admin if absent. */
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
