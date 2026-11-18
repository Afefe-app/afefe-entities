package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingOjtMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingOjtMilestoneRepository extends JpaRepository<TrainingOjtMilestone, UUID> {

    List<TrainingOjtMilestone> findByTraining_IdOrderByPositionAsc(UUID trainingId);

    Optional<TrainingOjtMilestone> findByIdAndTraining_Id(UUID id, UUID trainingId);

    long countByTraining_Id(UUID trainingId);
}
