package com.ocean.afefe.entities.domain.event;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class AbstractDomainEvent implements DomainEvent{
    protected final UUID eventId;
    protected final Instant occurredAt;

    protected AbstractDomainEvent(){
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
    }
}
