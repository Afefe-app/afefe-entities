package com.ocean.afefe.entities.modules.calendar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.tensorpoint.toolkit.tpointcore.commons.TimeZone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarEvent extends BaseUUIDEntity {

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime fromTime;

    @Column(nullable = false)
    private LocalTime toTime;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TimeZone timeZone;

    @ManyToOne
    private Course assignedCourse;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String location;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CalendarEventType eventType;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User updatedBy;
}
