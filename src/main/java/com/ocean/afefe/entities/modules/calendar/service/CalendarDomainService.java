package com.ocean.afefe.entities.modules.calendar.service;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.calendar.model.CalendarEvent;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;

import java.util.UUID;

public interface CalendarDomainService {

    CalendarEvent validateCalendarEventExistence(UUID calendarEventId, Organization organization, Instructor instructor);

    CalendarEvent validateCalendarEventExistence(UUID calendarEventId, Organization organization, Trainer trainer);
}
