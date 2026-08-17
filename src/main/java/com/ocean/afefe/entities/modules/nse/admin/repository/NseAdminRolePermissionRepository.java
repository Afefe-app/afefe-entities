package com.ocean.afefe.entities.modules.nse.admin.repository;

import com.ocean.afefe.entities.modules.nse.admin.models.NseAdminRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NseAdminRolePermissionRepository extends JpaRepository<NseAdminRolePermission, UUID> {
    List<NseAdminRolePermission> findByRole_Id(UUID roleId);

    void deleteByRole_Id(UUID roleId);
}
