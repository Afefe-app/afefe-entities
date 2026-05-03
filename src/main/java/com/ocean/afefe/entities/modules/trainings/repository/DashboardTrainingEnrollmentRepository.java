package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface DashboardTrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, UUID> {

    @Query("""
        SELECT COUNT(DISTINCT te.user.id)
        FROM TrainingEnrollment te
        WHERE te.training.trainer = :trainer
          AND te.org = :org
    """)
    long countDistinctLearnersByTrainerAndOrg(@Param("trainer") Trainer trainer, @Param("org") Organization org);

    @Query("""
        SELECT COUNT(DISTINCT te.user.id)
        FROM TrainingEnrollment te
        WHERE te.training.trainer = :trainer
          AND te.org = :org
          AND te.createdAt >= :start
          AND te.createdAt < :end
    """)
    long countDistinctLearnersByTrainerAndOrgAndCreatedAtBetween(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query("""
        SELECT SUM(te.training.price)
        FROM TrainingEnrollment te
        WHERE te.training.trainer = :trainer
          AND te.org = :org
    """)
    BigDecimal sumRevenueByTrainerAndOrg(@Param("trainer") Trainer trainer, @Param("org") Organization org);

    @Query("""
        SELECT SUM(te.training.price)
        FROM TrainingEnrollment te
        WHERE te.training.trainer = :trainer
          AND te.org = :org
          AND te.createdAt >= :start
          AND te.createdAt < :end
    """)
    BigDecimal sumRevenueByTrainerAndOrgAndCreatedAtBetween(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query("""
        SELECT te
        FROM TrainingEnrollment te
        JOIN FETCH te.user
        JOIN FETCH te.training
        WHERE te.training.trainer = :trainer
          AND te.org = :org
          AND te.createdAt >= :start
          AND te.createdAt < :end
        ORDER BY te.createdAt ASC
    """)
    List<TrainingEnrollment> findSalesEnrollmentsByTrainerAndOrgAndCreatedAtBetween(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query("""
        SELECT te
        FROM TrainingEnrollment te
        JOIN FETCH te.user
        JOIN FETCH te.training
        WHERE te.training.trainer = :trainer
          AND te.org = :org
          AND (
            (te.createdAt >= :start AND te.createdAt < :end)
            OR (te.startedAt IS NOT NULL AND te.startedAt >= :start AND te.startedAt < :end)
          )
        ORDER BY te.createdAt ASC
    """)
    List<TrainingEnrollment> findActivityEnrollmentsByTrainerAndOrgAndRange(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query("""
        SELECT te
        FROM TrainingEnrollment te
        JOIN FETCH te.user
        JOIN FETCH te.training
        WHERE te.training.trainer = :trainer
          AND te.org = :org
        ORDER BY te.createdAt DESC
    """)
    List<TrainingEnrollment> findRecentEnrollmentsByTrainerAndOrg(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            Pageable pageable
    );
}
