package com.ocean.afefe.entities.modules.nse.admin.repository;

import com.ocean.afefe.entities.modules.nse.admin.models.NseAdminInvite;
import com.ocean.afefe.entities.modules.nse.admin.models.NseAdminInviteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NseAdminInviteRepository extends JpaRepository<NseAdminInvite, UUID> {

    Optional<NseAdminInvite> findByTokenHashAndStatus(String tokenHash, NseAdminInviteStatus status);

    Optional<NseAdminInvite> findByOrg_IdAndEmailAddressIgnoreCaseAndStatus(
            UUID orgId,
            String emailAddress,
            NseAdminInviteStatus status
    );

    Page<NseAdminInvite> findByOrg_IdOrderByCreatedAtDesc(UUID orgId, Pageable pageable);

    Page<NseAdminInvite> findByOrg_IdAndStatusOrderByCreatedAtDesc(
            UUID orgId,
            NseAdminInviteStatus status,
            Pageable pageable
    );

    Optional<NseAdminInvite> findByIdAndOrg_Id(UUID inviteId, UUID orgId);
}
