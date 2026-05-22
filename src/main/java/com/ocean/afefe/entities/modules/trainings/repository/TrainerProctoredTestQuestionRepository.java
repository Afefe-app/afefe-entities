package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainerProctoredTestQuestionRepository extends JpaRepository<TrainerProctoredTestQuestion, UUID> {

    List<TrainerProctoredTestQuestion> findByProctoredTest_IdOrderByPositionAsc(UUID proctoredTestId);
    long countByProctoredTest_Id(UUID proctoredTestId);

    void deleteByProctoredTest_Id(UUID proctoredTestId);
}
