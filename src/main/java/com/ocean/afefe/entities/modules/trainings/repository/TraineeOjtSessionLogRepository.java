package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TraineeOjtSessionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeOjtSessionLogRepository
        extends JpaRepository<TraineeOjtSessionLog, UUID>, JpaSpecificationExecutor<TraineeOjtSessionLog> {

    Page<TraineeOjtSessionLog> findByEnrollment_IdOrderBySessionDateDescCreatedAtDesc(
            UUID enrollmentId, Pageable pageable);

    Optional<TraineeOjtSessionLog> findByIdAndEnrollment_Id(UUID id, UUID enrollmentId);

    List<TraineeOjtSessionLog> findByEnrollment_IdOrderBySessionDateAsc(UUID enrollmentId);

    @Query("SELECT COALESCE(SUM(l.durationHours), 0) FROM TraineeOjtSessionLog l WHERE l.enrollment.id = :enrollmentId")
    long sumDurationHoursByEnrollmentId(@Param("enrollmentId") UUID enrollmentId);

    @Query("""
            SELECT COALESCE(SUM(l.durationHours), 0)
            FROM TraineeOjtSessionLog l
            WHERE l.org.id = :orgId AND l.enrollment.training.id = :trainingId
            """)
    long sumDurationHoursByOrgIdAndTrainingId(@Param("orgId") UUID orgId, @Param("trainingId") UUID trainingId);

    boolean existsByEnrollment_IdAndSessionDateGreaterThanEqual(UUID enrollmentId, LocalDate sessionDate);

    Page<TraineeOjtSessionLog> findByOrg_IdAndEnrollment_IdOrderBySessionDateDescCreatedAtDesc(
            UUID orgId, UUID enrollmentId, Pageable pageable);

    @Query("""
            SELECT l FROM TraineeOjtSessionLog l
            JOIN FETCH l.enrollment e
            JOIN FETCH e.user u
            LEFT JOIN FETCH l.supervisorUser
            WHERE l.org.id = :orgId AND e.training.id = :trainingId
            ORDER BY l.sessionDate DESC, l.createdAt DESC
            """)
    Page<TraineeOjtSessionLog> findByOrgAndTrainingWithEnrollmentUser(
            @Param("orgId") UUID orgId,
            @Param("trainingId") UUID trainingId,
            Pageable pageable);
}
