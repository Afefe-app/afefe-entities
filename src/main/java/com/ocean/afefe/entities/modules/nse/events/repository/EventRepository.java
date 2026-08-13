package com.ocean.afefe.entities.modules.nse.events.repository;

import com.ocean.afefe.entities.modules.nse.events.models.Event;
import com.ocean.afefe.entities.modules.nse.events.models.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByIdAndOrg_Id(UUID id, UUID orgId);
    List<Event> findByOrg_IdAndStatusOrderByStartAtAsc(UUID orgId, EventStatus status);
    List<Event> findByOrg_IdAndStatusAndStartAtAfterOrderByStartAtAsc(UUID orgId, EventStatus status, Instant startAt);
}
