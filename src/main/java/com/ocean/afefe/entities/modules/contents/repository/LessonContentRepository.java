package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.LessonContent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonContentRepository extends JpaRepository<LessonContent, UUID> {
    List<LessonContent> findAllByLessonIdOrderByCreatedAtAsc(UUID lessonId);

    /** Batch fetch by lesson ids; order by createdAt within each lesson. */
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"lesson"})
    List<LessonContent> findAllByLesson_IdInOrderByCreatedAtAsc(List<UUID> lessonIds);
}
