package com.ocean.afefe.entities.domain.event;

import java.util.Set;

public interface DomainEvent {

    default String topic(){ return null; }
    default String messageKey() { return null; }
    default String payloadJson() { return  "{}"; }

    String getType();
    Set<EventCategory> getCategory();
}
