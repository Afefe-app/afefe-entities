package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TraineeQuiz;
import com.ocean.afefe.entities.modules.nse.trainings.models.TraineeQuizAttempt;
import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TraineeQuizAttemptRepository extends JpaRepository<TraineeQuizAttempt, UUID> {
    long countByEnrollmentAndQuiz(TrainingEnrollment enrollment, TraineeQuiz quiz);
    List<TraineeQuizAttempt> findByEnrollmentAndQuizOrderByAttemptNumberDesc(TrainingEnrollment enrollment, TraineeQuiz quiz);
    Optional<TraineeQuizAttempt> findByIdAndEnrollment_Id(UUID attemptId, UUID enrollmentId);
    List<TraineeQuizAttempt> findByEnrollment_IdOrderByQuiz_IdAscAttemptNumberDesc(UUID enrollmentId);
}
