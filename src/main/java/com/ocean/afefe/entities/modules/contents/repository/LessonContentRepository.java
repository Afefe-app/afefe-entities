package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.LessonContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LessonContentRepository extends JpaRepository<LessonContent, UUID> {
    List<LessonContent> findAllByLessonIdOrderByCreatedAtAsc(UUID lessonId);

    /** Batch fetch by lesson ids; order by createdAt within each lesson. */
    List<LessonContent> findAllByLesson_IdInOrderByCreatedAtAsc(List<UUID> lessonIds);
}
