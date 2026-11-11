package com.ocean.afefe.entities.modules.calendar.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.calendar.model.CalendarEvent;
import com.ocean.afefe.entities.modules.calendar.model.CalendarEventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, UUID>, QuerydslPredicateExecutor<CalendarEvent>, JpaSpecificationExecutor<CalendarEvent> {

    CalendarEvent findFirstByIdAndCreatedBy(UUID id, User user);

    Optional<CalendarEvent> findByIdAndAssignedTraining_Id(UUID id, UUID trainingId);

    List<CalendarEvent> findAllByCreatedByAndDateBetweenOrderByDateAscFromTimeAsc(User createdBy, LocalDate startDate, LocalDate endDate);

    Page<CalendarEvent> findByAssignedTraining_IdOrderByDateAscFromTimeAsc(UUID trainingId, Pageable pageable);

    Page<CalendarEvent> findByAssignedTraining_IdAndEventTypeOrderByDateAscFromTimeAsc(
            UUID trainingId, CalendarEventType eventType, Pageable pageable);

    @Query("""
            SELECT ce FROM CalendarEvent ce
            WHERE ce.assignedTraining.id = :trainingId
              AND ce.status NOT IN :excludedStatuses
            ORDER BY ce.date DESC, ce.fromTime DESC
            """)
    Page<CalendarEvent> findByAssignedTraining_IdExcludingStatusesOrderByDateDescFromTimeDesc(
            @Param("trainingId") UUID trainingId,
            @Param("excludedStatuses") java.util.List<com.ocean.afefe.entities.modules.calendar.model.CalendarEventStatus> excludedStatuses,
            Pageable pageable);

    long countByAssignedTraining_IdIn(List<UUID> trainingIds);

    Page<CalendarEvent> findAllByAssignedTraining_Org_IdOrderByDateDescFromTimeDesc(UUID orgId, Pageable pageable);

    /**
     * Optional filters must not use {@code :param IS NULL} — PostgreSQL cannot type those binds
     * (ERROR: could not determine data type of parameter $N). Callers pass boolean flags and
     * always-non-null date bounds (use open-ended sentinels when a bound is absent).
     */
    @Query("""
            SELECT ce FROM CalendarEvent ce
            LEFT JOIN FETCH ce.assignedTraining t
            LEFT JOIN FETCH ce.assignedCourse c
            LEFT JOIN FETCH ce.createdBy
            WHERE t.org.id = :orgId
              AND (:filterTraining = false OR t.id = :trainingId)
              AND (:filterCourse = false OR c.id = :courseId)
              AND ce.date >= :fromDate
              AND ce.date <= :toDate
              AND ce.status <> com.ocean.afefe.entities.modules.calendar.model.CalendarEventStatus.CANCELED
            ORDER BY ce.date DESC, ce.fromTime DESC
            """)
    List<CalendarEvent> findSessionsByOrgAndFilters(
            @Param("orgId") UUID orgId,
            @Param("filterTraining") boolean filterTraining,
            @Param("trainingId") UUID trainingId,
            @Param("filterCourse") boolean filterCourse,
            @Param("courseId") UUID courseId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    @Query("""
            SELECT COUNT(ce) FROM CalendarEvent ce
            WHERE ce.assignedTraining.org.id = :orgId
              AND ce.status <> com.ocean.afefe.entities.modules.calendar.model.CalendarEventStatus.CANCELED
              AND (:filterTraining = false OR ce.assignedTraining.id = :trainingId)
              AND ce.date >= :fromDate
              AND ce.date <= :toDate
            """)
    long countSessionsByOrgAndFilters(
            @Param("orgId") UUID orgId,
            @Param("filterTraining") boolean filterTraining,
            @Param("trainingId") UUID trainingId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    long countByAssignedTraining_Org_IdAndDateAndStatusNot(
            UUID orgId, LocalDate date, com.ocean.afefe.entities.modules.calendar.model.CalendarEventStatus status);

    Optional<CalendarEvent> findByIdAndAssignedTraining_Org_Id(UUID id, UUID orgId);
}
