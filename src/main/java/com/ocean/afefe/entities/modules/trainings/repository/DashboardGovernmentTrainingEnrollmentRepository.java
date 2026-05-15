package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Org-scoped programme (training) enrollment aggregates for government dashboards.
 */
@Repository
public interface DashboardGovernmentTrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, UUID> {

    @Query("""
            SELECT COALESCE(SUM(te.training.price), 0)
            FROM TrainingEnrollment te
            WHERE te.org.id = :orgId
              AND te.createdAt >= :start
              AND te.createdAt < :end
            """)
    BigDecimal sumEnrollmentRevenueByOrgAndCreatedAtBetween(
            @Param("orgId") UUID orgId,
            @Param("start") Instant start,
            @Param("end") Instant end);

    @Query("""
            SELECT COUNT(te)
            FROM TrainingEnrollment te
            WHERE te.org.id = :orgId
              AND te.startedAt IS NOT NULL
              AND te.startedAt >= :start
              AND te.startedAt < :end
            """)
    long countProgrammeEnrollmentsStartedBetweenForOrg(
            @Param("orgId") UUID orgId,
            @Param("start") Instant start,
            @Param("end") Instant end);

    @Query("""
            SELECT COUNT(te)
            FROM TrainingEnrollment te
            WHERE te.org.id = :orgId
              AND te.status = :completed
              AND te.completedAt IS NOT NULL
              AND te.completedAt >= :start
              AND te.completedAt < :end
            """)
    long countProgrammeEnrollmentsCompletedBetweenForOrg(
            @Param("orgId") UUID orgId,
            @Param("completed") EnrollmentStatus completed,
            @Param("start") Instant start,
            @Param("end") Instant end);
}
