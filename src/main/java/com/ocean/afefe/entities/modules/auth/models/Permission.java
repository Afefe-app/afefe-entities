package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseUUIDEntity {

    @Column(name = "permission_key", nullable = false, unique = true)
    private String permissionKey;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean systemDefined = true;
}
