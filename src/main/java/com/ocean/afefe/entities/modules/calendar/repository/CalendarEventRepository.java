package com.ocean.afefe.entities.modules.calendar.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.calendar.model.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, UUID>, QuerydslPredicateExecutor<CalendarEvent> {

    CalendarEvent findFirstByIdAndCreatedBy(UUID id, User user);
}
