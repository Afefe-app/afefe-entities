package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import com.ocean.afefe.entities.modules.trainings.models.TrainingSessionAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingSessionAttendanceRepository extends JpaRepository<TrainingSessionAttendance, UUID> {

    Optional<TrainingSessionAttendance> findByEnrollmentAndCalendarEvent_Id(
            TrainingEnrollment enrollment, UUID calendarEventId);

    Page<TrainingSessionAttendance> findByEnrollment_IdOrderByMarkedAtDesc(UUID enrollmentId, Pageable pageable);

    List<TrainingSessionAttendance> findByOrgAndEnrollment_Training_Id(Organization org, UUID trainingId);

    @Query("""
            SELECT a
            FROM TrainingSessionAttendance a
            JOIN FETCH a.enrollment e
            JOIN FETCH e.user
            JOIN FETCH e.training
            JOIN FETCH a.calendarEvent
            WHERE a.org = :org
            ORDER BY a.calendarEvent.date DESC, a.markedAt DESC
            """)
    Page<TrainingSessionAttendance> findAllByOrgWithDetails(@Param("org") Organization org, Pageable pageable);

    @Query("""
            SELECT a
            FROM TrainingSessionAttendance a
            JOIN FETCH a.enrollment e
            JOIN FETCH e.user
            JOIN FETCH e.training
            JOIN FETCH a.calendarEvent
            WHERE a.org = :org
              AND (
                LOWER(COALESCE(e.user.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(e.user.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(e.training.title, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            ORDER BY a.calendarEvent.date DESC, a.markedAt DESC
            """)
    Page<TrainingSessionAttendance> searchAllByOrgWithDetails(
            @Param("org") Organization org,
            @Param("search") String search,
            Pageable pageable);

    @Query("""
            SELECT a
            FROM TrainingSessionAttendance a
            JOIN FETCH a.enrollment e
            JOIN FETCH e.training
            JOIN FETCH a.calendarEvent
            WHERE a.org = :org AND e.user.id = :userId
            ORDER BY a.calendarEvent.date ASC, a.markedAt ASC
            """)
    List<TrainingSessionAttendance> findByOrgAndEnrollment_User_IdWithDetails(
            @Param("org") Organization org,
            @Param("userId") UUID userId);
}
