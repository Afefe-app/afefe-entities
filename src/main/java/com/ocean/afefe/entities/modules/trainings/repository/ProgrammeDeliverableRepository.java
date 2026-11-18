package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.ProgrammeDeliverable;
import com.ocean.afefe.entities.modules.trainings.models.ProgrammeItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgrammeDeliverableRepository extends JpaRepository<ProgrammeDeliverable, UUID> {

    List<ProgrammeDeliverable> findByTraining_IdAndOrg_IdOrderByDueDateAscCreatedAtDesc(UUID trainingId, UUID orgId);

    long countByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId);

    long countByTraining_IdAndOrg_IdAndStatus(UUID trainingId, UUID orgId, ProgrammeItemStatus status);

    Optional<ProgrammeDeliverable> findByIdAndTraining_IdAndOrg_Id(UUID id, UUID trainingId, UUID orgId);
}
