package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.ProgrammeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgrammeReportRepository extends JpaRepository<ProgrammeReport, UUID> {

    List<ProgrammeReport> findByTraining_IdAndOrg_IdOrderByCreatedAtDesc(UUID trainingId, UUID orgId);

    Optional<ProgrammeReport> findByIdAndTraining_IdAndOrg_Id(UUID id, UUID trainingId, UUID orgId);

    long countByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId);
}
