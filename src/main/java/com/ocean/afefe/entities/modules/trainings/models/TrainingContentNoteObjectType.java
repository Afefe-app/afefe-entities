package com.ocean.afefe.entities.modules.trainings.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrainingContentNoteObjectType implements Enumerable {

    TRAINING("Training"),
    MONTH("Month"),
    WEEK("Week"),
    CONTENT_ITEM("Content item"),
    BLOCK("Block");

    private final String description;
}
