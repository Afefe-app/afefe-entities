package com.ocean.afefe.entities.modules.calendar;

import com.ocean.afefe.entities.modules.calendar.service.CalendarDomainServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        CalendarDomainServiceImpl.class
})
@Configuration
public class CalendarModule {
}
