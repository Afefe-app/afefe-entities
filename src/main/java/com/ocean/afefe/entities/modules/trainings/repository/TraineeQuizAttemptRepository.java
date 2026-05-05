package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.TraineeQuiz;
import com.ocean.afefe.entities.modules.trainings.models.TraineeQuizAttempt;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeQuizAttemptRepository extends JpaRepository<TraineeQuizAttempt, UUID> {

    long countByEnrollmentAndQuiz(TrainingEnrollment enrollment, TraineeQuiz quiz);

    List<TraineeQuizAttempt> findByEnrollmentAndQuizOrderByAttemptNumberDesc(
            TrainingEnrollment enrollment, TraineeQuiz quiz);

    Optional<TraineeQuizAttempt> findByIdAndEnrollment_Id(UUID attemptId, UUID enrollmentId);

    @Query("""
            SELECT ta.enrollment.user.id, COALESCE(SUM(COALESCE(ta.scorePercent, 0)), 0)
            FROM TraineeQuizAttempt ta
            WHERE ta.attemptStatus = com.ocean.afefe.entities.modules.trainings.models.TraineeQuizAttemptStatus.GRADED
              AND ta.submittedAt IS NOT NULL
              AND ta.submittedAt >= :startDate
              AND ta.submittedAt < :endDate
              AND ta.enrollment.org = :org
            GROUP BY ta.enrollment.user.id
            ORDER BY COALESCE(SUM(COALESCE(ta.scorePercent, 0)), 0) DESC
            """)
    List<Object[]> sumTraineeQuizScorePercentByOrgAndSubmittedAtBetween(
            @Param("org") Organization org,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            Pageable pageable);
}
