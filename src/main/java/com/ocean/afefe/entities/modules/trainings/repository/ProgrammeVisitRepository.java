package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.ProgrammeVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgrammeVisitRepository extends JpaRepository<ProgrammeVisit, UUID> {

    List<ProgrammeVisit> findByTraining_IdAndOrg_IdOrderByVisitDateDescCreatedAtDesc(UUID trainingId, UUID orgId);

    long countByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId);

    Optional<ProgrammeVisit> findFirstByTraining_IdAndOrg_IdAndNextVisitTrueOrderByVisitDateAsc(UUID trainingId, UUID orgId);

    Optional<ProgrammeVisit> findByIdAndTraining_IdAndOrg_Id(UUID id, UUID trainingId, UUID orgId);
}
