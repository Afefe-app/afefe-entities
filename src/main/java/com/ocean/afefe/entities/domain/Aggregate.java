package com.ocean.afefe.entities.domain;

import java.util.Collection;
import java.util.List;

public interface Aggregate {

    List<DomainEvent> getDomainEvents();

    default void registerDomainEvent(DomainEvent event){
        List<DomainEvent> domainEvents = getDomainEvents();
        if(domainEvents != null){
            domainEvents.add(event);
        }
    }

    default void registerDomainEvent(Collection<DomainEvent> events){
        List<DomainEvent> domainEvents = getDomainEvents();
        if(domainEvents != null){
            domainEvents.addAll(events);
        }
    }

    default void clearDomainEvents(){
        List<DomainEvent> domainEvents = getDomainEvents();
        if(domainEvents != null && !domainEvents.isEmpty()){
            domainEvents.clear();
        }
    }
}
