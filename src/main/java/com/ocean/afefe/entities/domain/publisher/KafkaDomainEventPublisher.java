package com.ocean.afefe.entities.domain.publisher;

import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.event.DomainEvent;
import com.ocean.afefe.entities.domain.event.EventCategory;
import com.tensorpoint.toolkit.common.Broker;
import com.tensorpoint.toolkit.modules.broker.BrokerMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class KafkaDomainEventPublisher implements DomainEventPublisher{

    private final BrokerMessageService brokerMessageService;

    @Override
    public void publishEvents(Aggregate aggregate) {
        List<DomainEvent> domainEvents = aggregate.getDomainEvents();
        for (DomainEvent domainEvent : domainEvents){
            brokerMessageService.createNewBrokerMessage(domainEvent.topic(), domainEvent.messageKey(), domainEvent.payloadJson(), Broker.KAFKA);
        }
    }

    @Override
    public boolean supports(EventCategory eventCategory) {
        return EventCategory.KAFKA.equals(eventCategory);
    }
}
