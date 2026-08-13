package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingContentBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingContentBlockRepository extends JpaRepository<TrainingContentBlock, UUID> {
    List<TrainingContentBlock> findByContentItem_IdOrderBySortOrderAsc(UUID contentItemId);
    List<TrainingContentBlock> findByContentItem_IdInOrderBySortOrderAsc(List<UUID> contentItemIds);
    Optional<TrainingContentBlock> findByIdAndContentItem_Id(UUID blockId, UUID contentItemId);
    List<TrainingContentBlock> findByContentItem_Week_Month_Training_IdOrderByContentItem_Week_PositionAscContentItem_PositionAscSortOrderAsc(UUID trainingId);
}
