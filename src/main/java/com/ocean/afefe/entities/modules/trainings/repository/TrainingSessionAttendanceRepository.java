package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import com.ocean.afefe.entities.modules.trainings.models.TrainingSessionAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingSessionAttendanceRepository extends JpaRepository<TrainingSessionAttendance, UUID> {

    Optional<TrainingSessionAttendance> findByEnrollmentAndCalendarEvent_Id(
            TrainingEnrollment enrollment, UUID calendarEventId);

    Page<TrainingSessionAttendance> findByEnrollment_IdOrderByMarkedAtDesc(UUID enrollmentId, Pageable pageable);

    @Query("""
            SELECT ta
            FROM TrainingSessionAttendance ta
            JOIN ta.calendarEvent ce
            JOIN ta.enrollment te
            WHERE te.org.id = :orgId
            ORDER BY ce.date DESC, ce.fromTime DESC, ta.markedAt DESC
            """)
    Page<TrainingSessionAttendance> findRowsByOrganizationIdOrderBySessionDateTime(
            @Param("orgId") UUID orgId,
            Pageable pageable);

    @Query("""
            SELECT ta
            FROM TrainingSessionAttendance ta
            JOIN ta.calendarEvent ce
            JOIN ta.enrollment te
            WHERE te.org.id = :orgId AND te.user.id = :userId
            ORDER BY ce.date DESC, ce.fromTime DESC, ta.markedAt DESC
            """)
    Page<TrainingSessionAttendance> findRowsByTraineeUserAndOrganization(
            @Param("orgId") UUID orgId,
            @Param("userId") UUID userId,
            Pageable pageable);
}
