package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TraineeQuiz;
import com.ocean.afefe.entities.modules.nse.trainings.models.TraineeQuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TraineeQuizQuestionRepository extends JpaRepository<TraineeQuizQuestion, UUID> {
    List<TraineeQuizQuestion> findByQuizOrderByPositionAsc(TraineeQuiz quiz);
    List<TraineeQuizQuestion> findByQuiz_IdOrderByPositionAsc(UUID quizId);
    Optional<TraineeQuizQuestion> findByIdAndQuiz_Id(UUID questionId, UUID quizId);
}
