package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingContentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingContentItemRepository extends JpaRepository<TrainingContentItem, UUID> {

    List<TrainingContentItem> findByWeek_IdOrderByPositionAsc(UUID weekId);

    List<TrainingContentItem> findByWeek_IdInOrderByPositionAsc(List<UUID> weekIds);
}
