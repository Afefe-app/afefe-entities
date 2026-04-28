package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TraineeQuizOption;
import com.ocean.afefe.entities.modules.trainings.models.TraineeQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TraineeQuizOptionRepository extends JpaRepository<TraineeQuizOption, UUID> {

    List<TraineeQuizOption> findByQuestionOrderByPositionAsc(TraineeQuizQuestion question);
}
