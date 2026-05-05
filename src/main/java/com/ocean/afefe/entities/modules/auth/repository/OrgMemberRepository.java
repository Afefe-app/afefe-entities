package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface OrgMemberRepository extends JpaRepository<OrgMember, UUID> {
    boolean existsByUserAndOrganization(User user, Organization organization);
    boolean existsByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
    OrgMember findFirstByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
    long countByOrganization(Organization organization);

    @Query("SELECT COUNT(om) FROM OrgMember om WHERE om.organization = :org AND om.joinedAt IS NOT NULL AND om.joinedAt >= :after")
    long countByOrganizationAndJoinedAtAfter(@Param("org") Organization org, @Param("after") Instant after);

    @Query("SELECT COUNT(om) FROM OrgMember om WHERE om.organization = :org AND om.joinedAt IS NOT NULL AND om.joinedAt <= :date")
    long countByOrganizationAndJoinedAtToDate(@Param("org") Organization org, @Param("date") Instant date);

    @Query("""
        SELECT DISTINCT om.user.id
        FROM OrgMember om
        WHERE om.organization = :org
          AND (
              LOWER(COALESCE(om.user.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(COALESCE(om.user.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          )
    """)
    List<UUID> findDistinctUserIdsByOrganizationAndSearch(
            @Param("org") Organization org,
            @Param("search") String search
    );

    @Query("""
        SELECT om FROM OrgMember om
        WHERE om.organization = :org
          AND om.joinedAt IS NOT NULL
          AND (:search IS NULL OR :search = ''
               OR LOWER(COALESCE(om.user.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(COALESCE(om.user.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%')))
        """)
    Page<OrgMember> findJoinedMembersByOrganizationWithOptionalSearch(
            @Param("org") Organization org,
            @Param("search") String search,
            Pageable pageable);

    @Query("""
        SELECT DISTINCT om FROM OrgMember om
        JOIN Enrollment e ON e.user = om.user AND e.org = om.organization
        WHERE om.organization = :org
          AND om.joinedAt IS NOT NULL
          AND e.course.id IN :courseIds
          AND (:search IS NULL OR :search = ''
               OR LOWER(COALESCE(om.user.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(COALESCE(om.user.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%')))
        """)
    Page<OrgMember> findJoinedMembersByOrganizationEnrolledInCoursesWithOptionalSearch(
            @Param("org") Organization org,
            @Param("courseIds") Collection<UUID> courseIds,
            @Param("search") String search,
            Pageable pageable);

    /**
     * Org members on a learning path either via {@link com.ocean.afefe.entities.modules.enrollments.models.LearningPathEnrollment}
     * or via course enrollment on a node of that path.
     */
    @Query("""
            SELECT DISTINCT om FROM OrgMember om
            WHERE om.organization = :org
              AND om.joinedAt IS NOT NULL
              AND (
                  EXISTS (
                    SELECT 1 FROM LearningPathEnrollment lpe
                    WHERE lpe.user = om.user AND lpe.organization = :org AND lpe.learningPath.id = :learningPathId
                  )
                  OR EXISTS (
                    SELECT 1 FROM LearningPathNode n
                    JOIN Enrollment e ON e.course = n.course AND e.org = om.organization
                    WHERE n.learningPath.id = :learningPathId AND e.user = om.user
                  )
              )
              AND (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(om.user.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(om.user.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<OrgMember> findJoinedMembersByOrganizationOnLearningPathOrCourseEnrollment(
            @Param("org") Organization org,
            @Param("learningPathId") UUID learningPathId,
            @Param("search") String search,
            Pageable pageable);
}
