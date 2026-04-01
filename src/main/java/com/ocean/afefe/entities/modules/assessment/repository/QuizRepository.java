package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    List<Quiz> findAllByLessonId(UUID lessonId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"lesson"})
    List<Quiz> findAllByLessonIdIn(List<UUID> lessonIds);

    List<Quiz> findAllByCourse_Id(UUID courseId);

    long countByCourse_Id(UUID courseId);
}
