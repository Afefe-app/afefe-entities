package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TraineeQuizOption;
import com.ocean.afefe.entities.modules.nse.trainings.models.TraineeQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TraineeQuizOptionRepository extends JpaRepository<TraineeQuizOption, UUID> {
    List<TraineeQuizOption> findByQuestionOrderByPositionAsc(TraineeQuizQuestion question);
    List<TraineeQuizOption> findByQuestion_IdInOrderByQuestion_PositionAscPositionAsc(List<UUID> questionIds);
}
