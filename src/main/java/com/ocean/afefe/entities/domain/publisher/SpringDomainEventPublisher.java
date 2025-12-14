package com.ocean.afefe.entities.domain.publisher;

import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.event.DomainEvent;
import com.ocean.afefe.entities.domain.event.EventCategory;
import com.tensorpoint.toolkit.event.publisher.CentralApplicationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringDomainEventPublisher implements DomainEventPublisher{

    private final CentralApplicationEventPublisher applicationEventPublisher;

    @Override
    public synchronized void publishEvents(Aggregate aggregate){
        List<DomainEvent> domainEvents = aggregate.getDomainEvents();
        for(DomainEvent event : domainEvents){
            applicationEventPublisher.publishEventAsync(event);
        }
        aggregate.clearDomainEvents();
    }

    @Override
    public boolean supports(EventCategory eventCategory) {
        return EventCategory.SPRING.equals(eventCategory);
    }
}
