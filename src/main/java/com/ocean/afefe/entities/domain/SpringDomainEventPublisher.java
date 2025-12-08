package com.ocean.afefe.entities.domain;

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
}
