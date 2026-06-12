package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.LearningPath;
import com.ocean.afefe.entities.modules.enrollments.models.LearningPathEnrollment;
import com.ocean.afefe.entities.modules.enrollments.models.LearningPathEnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningPathEnrollmentRepository extends JpaRepository<LearningPathEnrollment, UUID> {

    Optional<LearningPathEnrollment> findByUserAndOrganization(User user, Organization organization);

    boolean existsByUserAndOrganizationAndLearningPath(User user, Organization organization, LearningPath learningPath);

    @Query("""
            SELECT lpe.learningPath.id, COUNT(lpe)
            FROM LearningPathEnrollment lpe
            WHERE lpe.organization = :org AND lpe.learningPath.id IN :pathIds
            GROUP BY lpe.learningPath.id
            """)
    List<Object[]> countGroupedByLearningPathIds(
            @Param("pathIds") Collection<UUID> pathIds,
            @Param("org") Organization org);

    long countByOrganization_Id(UUID organizationId);

    @Query("""
            SELECT COUNT(DISTINCT lpe.learningPath.id)
            FROM LearningPathEnrollment lpe
            WHERE lpe.organization.id = :orgId
            """)
    long countDistinctLearningPathsByOrganization_Id(@Param("orgId") UUID orgId);

    Optional<LearningPathEnrollment> findByIdAndOrganization(UUID id, Organization organization);

    @Query("""
            SELECT lpe FROM LearningPathEnrollment lpe
            JOIN FETCH lpe.learningPath
            WHERE lpe.organization = :org AND lpe.user.id IN :userIds
            """)
    List<LearningPathEnrollment> findByOrganizationAndUser_IdInWithLearningPath(
            @Param("org") Organization org,
            @Param("userIds") Collection<UUID> userIds);

    @Query("""
            SELECT lpe.learningPath.id, lpe.learningPath.title, COUNT(lpe)
            FROM LearningPathEnrollment lpe
            WHERE lpe.organization = :org
              AND (:assignedAfter IS NULL OR lpe.assignedAt >= :assignedAfter)
              AND (:assignedBefore IS NULL OR lpe.assignedAt < :assignedBefore)
            GROUP BY lpe.learningPath.id, lpe.learningPath.title
            """)
    List<Object[]> countGroupedByLearningPathForOrganization(
            @Param("org") Organization org,
            @Param("assignedAfter") java.time.Instant assignedAfter,
            @Param("assignedBefore") java.time.Instant assignedBefore);

    @Query("""
            SELECT lpe.learningPath.id, AVG(lpe.progressPercent)
            FROM LearningPathEnrollment lpe
            WHERE lpe.organization = :org
              AND lpe.learningPath.id IN :pathIds
              AND lpe.progressPercent IS NOT NULL
            GROUP BY lpe.learningPath.id
            """)
    List<Object[]> averageProgressPercentGroupedByLearningPathIds(
            @Param("pathIds") Collection<UUID> pathIds,
            @Param("org") Organization org);

    @Query("""
            SELECT AVG(lpe.progressPercent) FROM LearningPathEnrollment lpe
            WHERE lpe.organization = :org
              AND lpe.learningPath.id = :learningPathId
              AND lpe.progressPercent IS NOT NULL
            """)
    Double averageProgressPercentByOrganizationAndLearningPath(
            @Param("org") Organization org,
            @Param("learningPathId") UUID learningPathId);

    @Query(
            value = """
            SELECT lpe FROM LearningPathEnrollment lpe
            JOIN FETCH lpe.user u
            WHERE lpe.organization.id = :organizationId
              AND lpe.learningPath.id = :learningPathId
              AND (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:status IS NULL OR lpe.status = :status)
            """,
            countQuery = """
            SELECT COUNT(lpe) FROM LearningPathEnrollment lpe
            JOIN lpe.user u
            WHERE lpe.organization.id = :organizationId
              AND lpe.learningPath.id = :learningPathId
              AND (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:status IS NULL OR lpe.status = :status)
            """)
    Page<LearningPathEnrollment> findPageByOrganizationAndLearningPath(
            @Param("organizationId") UUID organizationId,
            @Param("learningPathId") UUID learningPathId,
            @Param("search") String search,
            @Param("status") LearningPathEnrollmentStatus status,
            Pageable pageable);
}
