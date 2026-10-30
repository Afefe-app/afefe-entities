package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import com.ocean.afefe.entities.modules.trainings.models.TrainingSessionAttendance;
import com.ocean.afefe.entities.modules.trainings.models.TrainingSessionAttendanceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingSessionAttendanceRepository extends JpaRepository<TrainingSessionAttendance, UUID> {

    List<TrainingSessionAttendance> findByEnrollment_Id(UUID enrollmentId);

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

    @Query("""
            SELECT ta
            FROM TrainingSessionAttendance ta
            JOIN FETCH ta.calendarEvent ce
            JOIN FETCH ta.enrollment te
            JOIN FETCH te.user u
            JOIN FETCH te.org o
            WHERE te.training.id = :trainingId
            ORDER BY ce.date DESC, ce.fromTime DESC, ta.markedAt DESC
            """)
    Page<TrainingSessionAttendance> findRowsByTrainingIdOrderBySessionDateTime(
            @Param("trainingId") UUID trainingId,
            Pageable pageable);

    @Query("""
            SELECT a
            FROM TrainingSessionAttendance a
            JOIN FETCH a.enrollment e
            JOIN FETCH e.training
            JOIN FETCH a.calendarEvent
            WHERE a.org = :org
              AND e.training.id = :trainingId
              AND e.user.id = :userId
            ORDER BY a.calendarEvent.date DESC, a.markedAt DESC
            """)
    List<TrainingSessionAttendance> findByOrgAndTrainingIdAndUserIdWithDetails(
            @Param("org") Organization org,
            @Param("trainingId") UUID trainingId,
            @Param("userId") UUID userId);

    long countByEnrollment_Id(UUID enrollmentId);

    long countByEnrollment_IdAndStatusIn(UUID enrollmentId, Collection<TrainingSessionAttendanceStatus> statuses);

    @Query("""
            SELECT a
            FROM TrainingSessionAttendance a
            JOIN FETCH a.calendarEvent ce
            JOIN FETCH a.enrollment e
            WHERE e.track.id = :trackId
              AND e.org.id = :orgId
              AND e.user.id = :userId
            ORDER BY ce.date DESC, a.markedAt DESC
            """)
    List<TrainingSessionAttendance> findByOrgAndTrackIdAndUserId(
            @Param("orgId") UUID orgId,
            @Param("trackId") UUID trackId,
            @Param("userId") UUID userId);

    @Query("""
            SELECT a
            FROM TrainingSessionAttendance a
            JOIN FETCH a.enrollment e
            JOIN FETCH e.user
            JOIN FETCH e.training t
            LEFT JOIN FETCH e.track
            JOIN FETCH a.calendarEvent ce
            WHERE a.org = :org
              AND (:trainingId IS NULL OR t.id = :trainingId)
              AND (:trackId IS NULL OR e.track.id = :trackId)
              AND (:courseId IS NULL OR ce.assignedCourse.id = :courseId)
              AND (:status IS NULL OR a.status = :status)
              AND (:fromDate IS NULL OR ce.date >= :fromDate)
              AND (:toDate IS NULL OR ce.date <= :toDate)
            ORDER BY ce.date DESC, ce.fromTime DESC, a.markedAt DESC
            """)
    List<TrainingSessionAttendance> findHubRowsByFilters(
            @Param("org") Organization org,
            @Param("trainingId") UUID trainingId,
            @Param("trackId") UUID trackId,
            @Param("courseId") UUID courseId,
            @Param("status") TrainingSessionAttendanceStatus status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    @Query("""
            SELECT a
            FROM TrainingSessionAttendance a
            JOIN FETCH a.enrollment e
            JOIN FETCH e.user
            JOIN FETCH a.calendarEvent ce
            WHERE a.org = :org
              AND ce.id = :calendarEventId
            """)
    List<TrainingSessionAttendance> findByOrgAndCalendarEventIdWithDetails(
            @Param("org") Organization org,
            @Param("calendarEventId") UUID calendarEventId);

    long countByOrgAndStatusAndCalendarEvent_Date(
            Organization org, TrainingSessionAttendanceStatus status, LocalDate date);

    long countByOrgAndCalendarEvent_Date(Organization org, LocalDate date);

    @Query("""
            SELECT COUNT(a)
            FROM TrainingSessionAttendance a
            WHERE a.org = :org
              AND a.status IN :statuses
              AND a.calendarEvent.date = :date
            """)
    long countByOrgAndStatusesAndDate(
            @Param("org") Organization org,
            @Param("statuses") Collection<TrainingSessionAttendanceStatus> statuses,
            @Param("date") LocalDate date);
}
