package com.ocean.afefe.entities.domain.publisher;

import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.event.EventCategory;

public interface DomainEventPublisher {
    void publishEvents(Aggregate aggregate);
    boolean supports(EventCategory eventCategory);
}
