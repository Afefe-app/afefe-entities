package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<Training, UUID>, JpaSpecificationExecutor<Training> {

    Optional<Training> findByIdAndOrg_Id(UUID id, UUID orgId);

    Optional<Training> findByIdAndTrainerAndOrg_Id(UUID id, Trainer trainer, UUID orgId);

    List<Training> findAllByTrainerAndOrg_IdOrderByTitleAsc(Trainer trainer, UUID orgId);
}
