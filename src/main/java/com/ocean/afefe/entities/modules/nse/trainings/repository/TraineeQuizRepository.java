package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TraineeQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TraineeQuizRepository extends JpaRepository<TraineeQuiz, UUID> {
    List<TraineeQuiz> findByTraining_Id(UUID trainingId);
    List<TraineeQuiz> findByTrainingWeek_Id(UUID weekId);
    Optional<TraineeQuiz> findByIdAndTraining_Id(UUID quizId, UUID trainingId);
    Optional<TraineeQuiz> findByTraining_IdAndTrainingWeek_Id(UUID trainingId, UUID weekId);
}
