package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.ProgrammeCorrectiveAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgrammeCorrectiveActionRepository extends JpaRepository<ProgrammeCorrectiveAction, UUID> {

    List<ProgrammeCorrectiveAction> findByTraining_IdAndOrg_IdOrderByDueDateAscCreatedAtDesc(UUID trainingId, UUID orgId);

    long countByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId);

    Optional<ProgrammeCorrectiveAction> findByIdAndTraining_IdAndOrg_Id(UUID id, UUID trainingId, UUID orgId);
}
