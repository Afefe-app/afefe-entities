package com.ocean.afefe.entities.modules.nse.admin.repository;

import com.ocean.afefe.entities.modules.nse.admin.models.NseAdminInviteRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NseAdminInviteRoleRepository extends JpaRepository<NseAdminInviteRole, UUID> {

    List<NseAdminInviteRole> findByInvite_Id(UUID inviteId);

    void deleteByInvite_Id(UUID inviteId);
}
