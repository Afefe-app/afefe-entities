package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingContentItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingContentItemRepository extends JpaRepository<TrainingContentItem, UUID> {
    List<TrainingContentItem> findByWeek_IdOrderByPositionAsc(UUID weekId);
    List<TrainingContentItem> findByWeek_IdInOrderByPositionAsc(List<UUID> weekIds);
    Optional<TrainingContentItem> findByIdAndWeek_Id(UUID contentItemId, UUID weekId);
    List<TrainingContentItem> findByWeek_Month_Training_IdOrderByWeek_PositionAscPositionAsc(UUID trainingId);
    long countByWeek_Id(UUID weekId);
}
