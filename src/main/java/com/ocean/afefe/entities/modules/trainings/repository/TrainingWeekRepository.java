package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingWeekRepository extends JpaRepository<TrainingWeek, UUID> {

    List<TrainingWeek> findByMonth_IdOrderByPositionAsc(UUID monthId);
}
