package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(
        name = "org_settings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_org_setting_organization",
                        columnNames = {"organization_id"}
                )
        },
        indexes = {
                @Index(name = "idx_org_setting_organization_id", columnList = "organization_id")
        }
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrgSetting extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false)
    private String settingsJson;
}
