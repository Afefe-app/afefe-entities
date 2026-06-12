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
        name = "role_permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}))
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    private Role role;

    @ManyToOne(optional = false)
    private Permission permission;
}
