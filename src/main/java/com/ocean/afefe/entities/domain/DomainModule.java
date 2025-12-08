package com.ocean.afefe.entities.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        SpringDomainEventPublisher.class
})
public class DomainModule {
}
