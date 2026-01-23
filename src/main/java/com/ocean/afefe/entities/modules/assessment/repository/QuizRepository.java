package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    Quiz findbyModuleId(UUID moduleId);

    List<Quiz> findAllByModuleIdIn(List<UUID> moduleIds);

    List<Quiz> findAllByLessonId(UUID lessonId);
}
