package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.QuizItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizItemRepository extends JpaRepository<QuizItem, UUID> {

    List<QuizItem> findAllByQuizIdOrderByPositionAsc(UUID quizId);
}
