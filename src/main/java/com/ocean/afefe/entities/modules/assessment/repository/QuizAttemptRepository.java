package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.Quiz;
import com.ocean.afefe.entities.modules.assessment.model.QuizAttempt;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, UUID> {

    List<QuizAttempt> findAllByQuizAndUser(Quiz quiz, User user);

    Optional<QuizAttempt> findByIdAndUser(UUID id, User user);

    long countByQuizAndUser(Quiz quiz, User user);
}
