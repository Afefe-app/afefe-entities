package com.ocean.afefe.entities.modules.contents.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentNoteObjectType implements Enumerable {

    COURSE("Course"),
    MODULE("Module"),
    LESSON("Lesson"),
    ASSET("Asset");

    private final String description;
}
