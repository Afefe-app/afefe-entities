package com.ocean.afefe.entities.modules.contents.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InteractiveActivityType {
    HOTSPOT("Hotspot"),
    DRAG_DROP("Drag drop"),
    LAB("Lab"),
    SIM("Sim");

    private final String description;
}
