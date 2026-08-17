package com.ocean.afefe.entities.modules.nse.onboarding.repository;

import com.ocean.afefe.entities.modules.nse.onboarding.models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IndustryRepository extends JpaRepository<Industry, UUID> {
    List<Industry> findByOrg_IdAndActiveTrueOrderByDisplayOrderAscNameAsc(UUID orgId);

    List<Industry> findByOrg_IdAndActiveTrueAndNameContainingIgnoreCaseOrderByDisplayOrderAscNameAsc(
            UUID orgId,
            String name
    );

    Optional<Industry> findByIdAndOrg_Id(UUID id, UUID orgId);

    Optional<Industry> findByOrg_IdAndNameIgnoreCase(UUID orgId, String name);
}
