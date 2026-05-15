package com.ocean.afefe.entities.modules.calendar.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.calendar.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, UUID>, QuerydslPredicateExecutor<CalendarEvent> {

    CalendarEvent findFirstByIdAndCreatedBy(UUID id, User user);

    Optional<CalendarEvent> findByIdAndAssignedTraining_Id(UUID id, UUID trainingId);

    List<CalendarEvent> findAllByCreatedByAndDateBetweenOrderByDateAscFromTimeAsc(User createdBy, LocalDate startDate, LocalDate endDate);

    Page<CalendarEvent> findAllByAssignedTraining_Org_IdOrderByDateDescFromTimeDesc(UUID orgId, Pageable pageable);
}
