package com.ocean.afefe.entities.modules.nse.auth.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfessionalDivision implements Enumerable {
    MECHANICAL("Mechanical Engineering"),
    ELECTRICAL("Electrical Engineering"),
    CIVIL("Civil Engineering"),
    CHEMICAL("Chemical Engineering"),
    AGRICULTURAL("Agricultural Engineering"),
    METALLURGICAL("Metallurgical & Materials Engineering"),
    PETROLEUM("Petroleum Engineering"),
    COMPUTER("Computer Engineering"),
    AEROSPACE("Aerospace Engineering"),
    OTHER("Other");

    private final String description;
}
