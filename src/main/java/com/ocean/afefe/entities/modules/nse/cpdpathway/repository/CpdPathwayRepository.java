package com.ocean.afefe.entities.modules.nse.cpdpathway.repository;

import com.ocean.afefe.entities.modules.nse.cpdpathway.models.CpdPathway;
import com.ocean.afefe.entities.modules.nse.cpdpathway.models.CpdPathwayStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CpdPathwayRepository extends JpaRepository<CpdPathway, UUID> {
    Optional<CpdPathway> findByIdAndOrg_Id(UUID id, UUID orgId);
    List<CpdPathway> findByOrg_IdAndStatusOrderByUpdatedAtDesc(UUID orgId, CpdPathwayStatus status);
}
