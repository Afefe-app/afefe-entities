package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingContentBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingContentBlockRepository extends JpaRepository<TrainingContentBlock, UUID> {

    List<TrainingContentBlock> findByContentItem_IdOrderBySortOrderAsc(UUID contentItemId);

    List<TrainingContentBlock> findByContentItem_IdInOrderBySortOrderAsc(List<UUID> contentItemIds);
}
