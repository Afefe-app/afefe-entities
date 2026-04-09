package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.LearningPathNode;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LearningPathNodeRepository extends JpaRepository<LearningPathNode, UUID> {
    @Query("""
        SELECT DISTINCT n.course.id
        FROM LearningPathNode n
        WHERE n.learningPath.id = :learningPathId
          AND n.learningPath.org = :org
    """)
    List<UUID> findDistinctCourseIdsByLearningPathIdAndOrg(
            @Param("learningPathId") UUID learningPathId,
            @Param("org") Organization org
    );

    @Query("""
        SELECT n
        FROM LearningPathNode n
        JOIN FETCH n.course c
        WHERE n.learningPath.id = :learningPathId
          AND n.learningPath.org = :org
        ORDER BY n.position ASC
    """)
    List<LearningPathNode> findByLearningPathIdAndOrgOrderByPositionAsc(
            @Param("learningPathId") UUID learningPathId,
            @Param("org") Organization org
    );

    @Query("""
        SELECT DISTINCT n.course.id
        FROM LearningPathNode n
        WHERE n.learningPath.id IN :learningPathIds
          AND n.learningPath.org = :org
          AND n.course.status = com.ocean.afefe.entities.modules.contents.models.CourseStatus.PUBLISHED
    """)
    List<UUID> findDistinctPublishedCourseIdsByLearningPathIdsAndOrg(
            @Param("learningPathIds") List<UUID> learningPathIds,
            @Param("org") Organization org
    );
}
