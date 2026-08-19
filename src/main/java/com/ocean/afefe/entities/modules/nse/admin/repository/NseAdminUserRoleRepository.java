package com.ocean.afefe.entities.modules.nse.admin.repository;

import com.ocean.afefe.entities.modules.nse.admin.models.NseAdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NseAdminUserRoleRepository extends JpaRepository<NseAdminUserRole, UUID> {
    List<NseAdminUserRole> findByOrg_IdAndUser_Id(UUID orgId, UUID userId);

    List<NseAdminUserRole> findByRole_Id(UUID roleId);

    long countByRole_Id(UUID roleId);

    void deleteByOrg_IdAndUser_Id(UUID orgId, UUID userId);
}
