package com.ocean.afefe.entities.modules.contents.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentLicenseType {
    STANDARD("Standard"),
    EXTENDED("Extended"),
    ENTERPRISE("Enterprise"),;

    private final String description;
}
