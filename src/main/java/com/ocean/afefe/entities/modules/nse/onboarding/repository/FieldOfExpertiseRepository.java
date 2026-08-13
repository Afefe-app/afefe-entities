package com.ocean.afefe.entities.modules.nse.onboarding.repository;

import com.ocean.afefe.entities.modules.nse.onboarding.models.FieldOfExpertise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FieldOfExpertiseRepository extends JpaRepository<FieldOfExpertise, UUID> {
    List<FieldOfExpertise> findByOrg_IdAndActiveTrueOrderByDisplayOrderAscNameAsc(UUID orgId);

    List<FieldOfExpertise> findByOrg_IdAndActiveTrueAndNameContainingIgnoreCaseOrderByDisplayOrderAscNameAsc(
            UUID orgId,
            String name
    );

    Optional<FieldOfExpertise> findByIdAndOrg_Id(UUID id, UUID orgId);

    Optional<FieldOfExpertise> findByOrg_IdAndNameIgnoreCase(UUID orgId, String name);
}
