package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TraineeQuiz;
import com.ocean.afefe.entities.modules.trainings.models.TraineeQuizAttempt;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeQuizAttemptRepository extends JpaRepository<TraineeQuizAttempt, UUID> {

    long countByEnrollmentAndQuiz(TrainingEnrollment enrollment, TraineeQuiz quiz);

    List<TraineeQuizAttempt> findByEnrollmentAndQuizOrderByAttemptNumberDesc(
            TrainingEnrollment enrollment, TraineeQuiz quiz);

    Optional<TraineeQuizAttempt> findByIdAndEnrollment_Id(UUID attemptId, UUID enrollmentId);
}
