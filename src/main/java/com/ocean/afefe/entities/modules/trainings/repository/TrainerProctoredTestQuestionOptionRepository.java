package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTestQuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainerProctoredTestQuestionOptionRepository extends JpaRepository<TrainerProctoredTestQuestionOption, UUID> {

    List<TrainerProctoredTestQuestionOption> findByQuestion_IdInOrderByQuestion_PositionAscPositionAsc(List<UUID> questionIds);

    void deleteByQuestion_ProctoredTest_Id(UUID proctoredTestId);
}
