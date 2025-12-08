package com.ocean.afefe.entities.domain;

public interface DomainEventPublisher {
    void publishEvents(Aggregate aggregate);
}
