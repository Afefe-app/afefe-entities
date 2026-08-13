package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingWeekRepository extends JpaRepository<TrainingWeek, UUID> {
    List<TrainingWeek> findByMonth_IdOrderByPositionAsc(UUID monthId);
    List<TrainingWeek> findByMonth_Training_IdOrderByMonth_PositionAscPositionAsc(UUID trainingId);
    Optional<TrainingWeek> findByIdAndMonth_Id(UUID weekId, UUID monthId);
    Optional<TrainingWeek> findByIdAndMonth_Training_Id(UUID weekId, UUID trainingId);
    long countByMonth_Id(UUID monthId);
}
