package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TraineeQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeQuizRepository extends JpaRepository<TraineeQuiz, UUID> {

    List<TraineeQuiz> findByTraining_Id(UUID trainingId);

    Optional<TraineeQuiz> findByIdAndTraining_Id(UUID quizId, UUID trainingId);
}
