package com.ocean.afefe.entities.modules.contents.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum LessonContentType implements Enumerable{
    VIDEO("Video"),
    TEXT("Text"),
    PDF("Pdf"),
    INTERACTIVE("Interactive"),
    SCORM("Scorm");

    private final String description;
}
