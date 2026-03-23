package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.Quiz;
import com.ocean.afefe.entities.modules.assessment.model.QuizAttempt;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, UUID> {

    List<QuizAttempt> findAllByQuizAndUser(Quiz quiz, User user);

    Optional<QuizAttempt> findByIdAndUser(UUID id, User user);

    long countByQuizAndUser(Quiz quiz, User user);

    @Query("""
        SELECT COUNT(DISTINCT qa.user.id)
        FROM QuizAttempt qa
        WHERE qa.quiz.id = :quizId
          AND qa.status = 'GRADED'
          AND qa.submittedAt IS NOT NULL
    """)
    long countDistinctGradedLearnersByQuizId(@Param("quizId") UUID quizId);

    @Query("""
        SELECT COUNT(DISTINCT qa.user.id)
        FROM QuizAttempt qa
        WHERE qa.quiz.id = :quizId
          AND qa.status = 'GRADED'
          AND qa.submittedAt IS NOT NULL
          AND qa.submittedAt >= :startDate
          AND qa.submittedAt <= :endDate
    """)
    long countDistinctGradedLearnersByQuizIdAndSubmittedAtBetween(
            @Param("quizId") UUID quizId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );
}
