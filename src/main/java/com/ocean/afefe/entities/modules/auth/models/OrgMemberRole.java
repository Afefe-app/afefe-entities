package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(
        name = "org_member_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"org_member_id", "role_id"}))
@AllArgsConstructor
@NoArgsConstructor
public class OrgMemberRole extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    private OrgMember orgMember;

    @ManyToOne(optional = false)
    private Role role;
}
