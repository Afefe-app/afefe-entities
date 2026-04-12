package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.contents.models.LearningPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface LearningPathRepository extends JpaRepository<LearningPath, UUID> {
    long countByOrg(Organization org);

    @Query("SELECT COUNT(lp) FROM LearningPath lp WHERE lp.org = :org AND lp.createdAt >= :after")
    long countByOrgAndCreatedAtAfter(@Param("org") Organization org, @Param("after") Instant after);

    @Query("SELECT COUNT(lp) FROM LearningPath lp WHERE lp.org = :org AND lp.createdAt <= :date")
    long countByOrgAndCreatedAtToDate(@Param("org") Organization org, @Param("date") Instant date);

    @Query("""
        SELECT lp.id
        FROM LearningPath lp
        WHERE lp.org = :org
          AND LOWER(COALESCE(lp.title, '')) LIKE LOWER(CONCAT('%', :search, '%'))
    """)
    List<UUID> findIdsByOrgAndTitleLike(
            @Param("org") Organization org,
            @Param("search") String search
    );
}
