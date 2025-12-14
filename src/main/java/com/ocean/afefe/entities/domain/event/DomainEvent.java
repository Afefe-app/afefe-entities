package com.ocean.afefe.entities.domain.event;

import java.util.Set;

public interface DomainEvent {

    String getType();
    Set<EventCategory> getCategory();
}
