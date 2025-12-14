package com.ocean.afefe.entities.domain.publisher;

import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.event.EventCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaDomainEventPublisher implements DomainEventPublisher{

    @Override
    public void publishEvents(Aggregate aggregate) {

    }

    @Override
    public boolean supports(EventCategory eventCategory) {
        return EventCategory.KAFKA.equals(eventCategory);
    }
}
