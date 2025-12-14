package com.ocean.afefe.entities.domain.publisher;

import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.event.DomainEvent;
import com.ocean.afefe.entities.domain.event.EventCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher{

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public synchronized void publishEvents(Aggregate aggregate){
        List<DomainEvent> domainEvents = aggregate.getDomainEvents();
        for(DomainEvent event : domainEvents){
            applicationEventPublisher.publishEvent(event);
        }
        aggregate.clearDomainEvents();
    }

    @Override
    public boolean supports(EventCategory eventCategory) {
        return EventCategory.SPRING.equals(eventCategory);
    }
}
