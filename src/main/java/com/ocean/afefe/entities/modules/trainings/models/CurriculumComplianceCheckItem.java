package com.ocean.afefe.entities.modules.trainings.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurriculumComplianceCheckItem {
    DOCUMENT_COMPLETE("DOCUMENT_COMPLETE", "Curriculum document uploaded", "Submission includes a readable curriculum document", 1),
    LEARNING_OBJECTIVES_DEFINED("LEARNING_OBJECTIVES_DEFINED", "Learning objectives defined", "Clear learning objectives are listed", 2),
    DURATION_SPECIFIED("DURATION_SPECIFIED", "Duration specified", "Programme duration is stated", 3),
    MONTH_WEEK_STRUCTURE("MONTH_WEEK_STRUCTURE", "Month/week structure", "Syllabus includes month and week breakdown", 4),
    OJT_HOURS_ALIGNED("OJT_HOURS_ALIGNED", "OJT hours aligned", "On-the-job training hours meet regulatory minimums", 5),
    ASSESSMENT_METHODS_DEFINED("ASSESSMENT_METHODS_DEFINED", "Assessment methods defined", "Assessment approach is documented", 6),
    TARGET_AUDIENCE_DEFINED("TARGET_AUDIENCE_DEFINED", "Target audience defined", "Intended trainee audience is specified", 7),
    PREREQUISITES_DOCUMENTED("PREREQUISITES_DOCUMENTED", "Prerequisites documented", "Entry prerequisites are listed where applicable", 8);

    private final String code;
    private final String label;
    private final String description;
    private final int sortOrder;

    public static CurriculumComplianceCheckItem fromCode(String code) {
        if (code == null || code.isBlank()) {
            return null;
        }
        for (CurriculumComplianceCheckItem item : values()) {
            if (item.code.equals(code)) {
                return item;
            }
        }
        return null;
    }
}
