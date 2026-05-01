package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingContentBlockType implements Enumerable {
    READING("Reading"),
    IMAGE("Image"),
    VIDEO_EMBED("Video embed"),
    RESOURCE_FILE("Resource file"),
    PRACTICE_QUIZ("Practice quiz");

    private final String description;
}
