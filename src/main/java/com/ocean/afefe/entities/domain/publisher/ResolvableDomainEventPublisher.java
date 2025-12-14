package com.ocean.afefe.entities.domain.publisher;

import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.event.DomainEvent;
import com.ocean.afefe.entities.domain.event.EventCategory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Primary
@Component
@RequiredArgsConstructor
public class ResolvableDomainEventPublisher implements DomainEventPublisher{

    private final List<DomainEventPublisher> domainEventPublishers;
    private final Map<EventCategory, DomainEventPublisher> categoryDomainEventPublisherMap = new HashMap<>();

    @Override
    public synchronized void publishEvents(Aggregate aggregate) {
        List<DomainEvent> domainEvents = aggregate.getDomainEvents();
        if(domainEvents != null && !domainEvents.isEmpty()){
            for(DomainEvent domainEvent : domainEvents){
                Set<EventCategory> uniqueCategories = domainEvent.getCategory();
                for(EventCategory eventCategory : uniqueCategories){
                    DomainEventPublisher domainEventPublisher = categoryDomainEventPublisherMap.get(eventCategory);
                    if(Objects.nonNull(domainEventPublisher)){
                        domainEventPublisher.publishEvents(aggregate);
                    }
                }
            }
        }
    }

    @Override
    public boolean supports(EventCategory eventCategory) {
        return false;
    }

    @PostConstruct()
    public void registerDomainEventsToCategoryMap(){
        EventCategory[] eventCategories = EventCategory.values();
        for(EventCategory eventCategory : eventCategories){
            DomainEventPublisher domainEventPublisher = domainEventPublishers.stream()
                    .filter(publisher -> publisher.supports(eventCategory))
                    .findFirst()
                    .orElse(null);
            if(Objects.nonNull(domainEventPublisher)){
                categoryDomainEventPublisherMap.put(eventCategory, domainEventPublisher);
            }
        }
    }
}
