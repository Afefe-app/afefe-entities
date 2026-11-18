package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.ProgrammeRisk;
import com.ocean.afefe.entities.modules.trainings.models.ProgrammeRiskSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgrammeRiskRepository extends JpaRepository<ProgrammeRisk, UUID> {

    List<ProgrammeRisk> findByTraining_IdAndOrg_IdOrderByCreatedAtDesc(UUID trainingId, UUID orgId);

    long countByTraining_IdAndOrg_IdAndSeverity(UUID trainingId, UUID orgId, ProgrammeRiskSeverity severity);

    long countByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId);

    Optional<ProgrammeRisk> findByIdAndTraining_IdAndOrg_Id(UUID id, UUID trainingId, UUID orgId);
}
