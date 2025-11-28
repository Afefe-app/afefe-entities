package com.ocean.afefe.entities.modules.contents.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonAssetType {
    VIDEO_URL("Video URL"),
    PDF_URL("PDF URL"),
    HTML("HTML"),
    SCORM_PKG("Scorm PKG"),;

    private final String description;
}
