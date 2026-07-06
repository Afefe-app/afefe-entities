package com.ocean.afefe.entities.modules.trainings.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumStatus;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumSubmission;
import com.ocean.afefe.entities.modules.trainings.support.TrainerCurriculumPresentationSupport;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public final class TrainerCurriculumViews {

    private TrainerCurriculumViews() {
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatusView {
        private String value;
        private String description;

        public static StatusView from(TrainerCurriculumStatus status) {
            if (status == null) {
                return new StatusView(StringValues.EMPTY_STRING, StringValues.EMPTY_STRING);
            }
            return new StatusView(status.name(), status.getDescription());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentView {
        private String url;
        private String name;
        private Long sizeBytes;
        private String mimeType;

        public static DocumentView from(TrainerCurriculumSubmission submission) {
            if (submission == null) {
                return DocumentView.builder().build();
            }
            return DocumentView.builder()
                    .url(submission.getDocumentUrl())
                    .name(submission.getDocumentName())
                    .sizeBytes(submission.getDocumentSizeBytes())
                    .mimeType(submission.getDocumentMimeType())
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WorkflowStepView {
        private Integer position;
        private String label;
        private String state;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MonthView {
        private Integer position;
        private String title;
        private String summary;
        @Builder.Default
        private List<String> learningObjectives = new ArrayList<>();
        @Builder.Default
        private List<String> assessmentMethods = new ArrayList<>();
        @Builder.Default
        private List<String> activities = new ArrayList<>();
        @Builder.Default
        private List<WeekView> weeks = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeekView {
        private Integer position;
        private String title;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubmissionCoreView {
        private List<String> prerequisites;
        private List<String> learningObjectives;
        private List<String> relatedCategories;
        private List<String> assessmentMethods;
        private DocumentView document;
        private List<WorkflowStepView> workflowProgress;
        private List<MonthView> months;

        public static SubmissionCoreView from(TrainerCurriculumSubmission submission) {
            if (submission == null) {
                return SubmissionCoreView.builder().build();
            }
            TrainerCurriculumStatus status = submission.getStatus();
            return SubmissionCoreView.builder()
                    .prerequisites(TrainerCurriculumPresentationSupport.parseStringList(submission.getPrerequisitesJson()))
                    .learningObjectives(TrainerCurriculumPresentationSupport.parseStringList(submission.getLearningObjectivesJson()))
                    .relatedCategories(TrainerCurriculumPresentationSupport.parseStringList(submission.getRelatedCategoriesJson()))
                    .assessmentMethods(TrainerCurriculumPresentationSupport.parseStringList(submission.getAssessmentMethodsJson()))
                    .document(DocumentView.from(submission))
                    .workflowProgress(TrainerCurriculumPresentationSupport.buildWorkflow(status))
                    .months(TrainerCurriculumPresentationSupport.parseMonths(submission.getMonthWeekStructureJson()))
                    .build();
        }
    }
}
