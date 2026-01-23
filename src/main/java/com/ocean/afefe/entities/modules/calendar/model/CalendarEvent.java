package com.ocean.afefe.entities.modules.calendar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import com.tensorpoint.toolkit.tpointcore.commons.TimeZone;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarEvent extends BaseUUIDEntity {

    @Builder.Default
    private String coverPhoto = StringValues.EMPTY_STRING;

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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CalendarEventStatus status;

    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @ManyToOne(optional = false)
    private User createdBy;

    @ManyToOne
    private User updatedBy;
}
