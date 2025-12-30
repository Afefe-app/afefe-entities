package com.ocean.afefe.entities.modules.calendar.service;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.calendar.model.CalendarEvent;
import com.ocean.afefe.entities.modules.calendar.repository.CalendarEventRepository;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.MessageUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalendarDomainServiceImpl implements CalendarDomainService {

    private final MessageUtil messageUtil;
    private final CalendarEventRepository calendarEventRepository;

    @Override
    public CalendarEvent validateCalendarEventExistence(UUID calendarEventId, Organization organization, Instructor instructor) {
        return Optional.ofNullable(calendarEventRepository.findFirstByIdAndCreatedBy(calendarEventId, instructor.getUser()))
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND,
                        messageUtil.getMessage("calendar.event.not.found")));
    }
}
