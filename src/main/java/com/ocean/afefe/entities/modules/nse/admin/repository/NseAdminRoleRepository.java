package com.ocean.afefe.entities.modules.nse.admin.repository;

import com.ocean.afefe.entities.modules.nse.admin.models.NseAdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NseAdminRoleRepository extends JpaRepository<NseAdminRole, UUID> {
    List<NseAdminRole> findByOrg_IdOrderByNameAsc(UUID orgId);

    Optional<NseAdminRole> findByIdAndOrg_Id(UUID id, UUID orgId);

    boolean existsByOrg_IdAndNameIgnoreCase(UUID orgId, String name);

    Optional<NseAdminRole> findByOrg_IdAndNameIgnoreCase(UUID orgId, String name);
}
