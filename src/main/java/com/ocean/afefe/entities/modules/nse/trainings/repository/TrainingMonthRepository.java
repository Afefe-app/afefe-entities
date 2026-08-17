package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.UUID;

public interface TrainingMonthRepository extends JpaRepository<TrainingMonth, UUID> {
    long countByTraining_Id(UUID trainingId);
    List<TrainingMonth> findByTraining_IdOrderByPositionAsc(UUID trainingId);
}
