package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingContentBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingContentBlockRepository extends JpaRepository<TrainingContentBlock, UUID> {

    List<TrainingContentBlock> findByContentItem_IdOrderBySortOrderAsc(UUID contentItemId);

    List<TrainingContentBlock> findByContentItem_IdInOrderBySortOrderAsc(List<UUID> contentItemIds);
    Optional<TrainingContentBlock> findByIdAndContentItem_Id(UUID blockId, UUID contentItemId);
    List<TrainingContentBlock> findByContentItem_Week_Month_Training_IdOrderByContentItem_Week_PositionAscContentItem_PositionAscSortOrderAsc(UUID trainingId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM TrainingContentBlock b WHERE b.contentItem.id IN :contentItemIds")
    int deleteByContentItemIdIn(@Param("contentItemIds") Collection<UUID> contentItemIds);
}
