package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TraineeQuiz;
import com.ocean.afefe.entities.modules.trainings.models.TraineeQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TraineeQuizQuestionRepository extends JpaRepository<TraineeQuizQuestion, UUID> {

    List<TraineeQuizQuestion> findByQuizOrderByPositionAsc(TraineeQuiz quiz);
}
