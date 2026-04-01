package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.Lesson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    List<Lesson> findAllByModuleId(UUID moduleId);

    List<Lesson> findAllByModuleIdOrderByPositionAsc(UUID moduleId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"module"})
    List<Lesson> findAllByModuleIdInOrderByPositionAsc(List<UUID> moduleIds);
}
