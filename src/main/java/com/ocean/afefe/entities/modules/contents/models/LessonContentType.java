package com.ocean.afefe.entities.modules.contents.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum LessonContentType {
    VIDEO("Dental Hygiene"),
    TEXT("BDS"),
    PDF("Dental Nursing"),
    INTERACTIVE("Interactive"),
    SCORM("Scorm");

    private final String description;
}
