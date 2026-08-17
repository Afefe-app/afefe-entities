package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.nse.trainings.models.Training;
import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingRepository extends JpaRepository<Training, UUID> {
    Optional<Training> findByIdAndTrainerAndOrg_Id(UUID id, Trainer trainer, UUID orgId);
    List<Training> findAllByTrainerAndOrg_IdAndStatusOrderByTitleAsc(Trainer trainer, UUID orgId, TrainingStatus status);
    List<Training> findByOrg_IdAndStatusOrderByUpdatedAtDesc(UUID orgId, TrainingStatus status);
    Optional<Training> findByIdAndOrg_Id(UUID id, UUID orgId);
}
