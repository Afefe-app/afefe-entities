package com.ocean.afefe.entities.modules.trainings.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ocean.afefe.entities.modules.trainings.models.CurriculumComplianceCheckItem;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumSubmission;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class CurriculumComplianceChecklistSupport {

    private CurriculumComplianceChecklistSupport() {
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChecklistStateEntry {
        private String code;
        private boolean checked;
        private Instant checkedAt;
        private UUID checkedByUserId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChecklistItemView {
        private String code;
        private String label;
        private String description;
        private int sortOrder;
        private boolean checked;
        private Instant checkedAt;
        private UUID checkedByUserId;
    }

    public static List<ChecklistItemView> buildChecklist(TrainerCurriculumSubmission submission) {
        Map<String, ChecklistStateEntry> stateByCode = parseState(submission != null ? submission.getComplianceChecklistJson() : null);
        List<ChecklistItemView> items = new ArrayList<>();
        for (CurriculumComplianceCheckItem template : CurriculumComplianceCheckItem.values()) {
            ChecklistStateEntry state = stateByCode.get(template.getCode());
            items.add(ChecklistItemView.builder()
                    .code(template.getCode())
                    .label(template.getLabel())
                    .description(template.getDescription())
                    .sortOrder(template.getSortOrder())
                    .checked(state != null && state.isChecked())
                    .checkedAt(state != null ? state.getCheckedAt() : null)
                    .checkedByUserId(state != null ? state.getCheckedByUserId() : null)
                    .build());
        }
        items.sort(Comparator.comparingInt(ChecklistItemView::getSortOrder));
        return items;
    }

    public static int computeComplianceScore(List<ChecklistItemView> items) {
        if (items == null || items.isEmpty()) {
            return 0;
        }
        long checked = items.stream().filter(ChecklistItemView::isChecked).count();
        return (int) Math.round(100.0 * checked / items.size());
    }

    @SneakyThrows
    public static String mergeAndSerialize(
            TrainerCurriculumSubmission submission,
            List<ChecklistUpdateRequest> updates,
            UUID reviewerUserId) {
        Map<String, ChecklistStateEntry> stateByCode = new LinkedHashMap<>();
        for (ChecklistStateEntry entry : parseStateList(submission.getComplianceChecklistJson())) {
            stateByCode.put(entry.getCode(), entry);
        }
        Instant now = Instant.now();
        for (ChecklistUpdateRequest update : updates) {
            if (update == null || CommonUtil.isNullOrEmpty(update.code())) {
                continue;
            }
            CurriculumComplianceCheckItem template = CurriculumComplianceCheckItem.fromCode(update.code());
            if (template == null) {
                continue;
            }
            ChecklistStateEntry entry = new ChecklistStateEntry();
            entry.setCode(template.getCode());
            entry.setChecked(update.checked());
            if (update.checked()) {
                entry.setCheckedAt(now);
                entry.setCheckedByUserId(reviewerUserId);
            } else {
                entry.setCheckedAt(null);
                entry.setCheckedByUserId(null);
            }
            stateByCode.put(template.getCode(), entry);
        }
        return CommonUtil.getServerMapper().writeValueAsString(new ArrayList<>(stateByCode.values()));
    }

    public record ChecklistUpdateRequest(String code, boolean checked) {
    }

    private static Map<String, ChecklistStateEntry> parseState(String json) {
        Map<String, ChecklistStateEntry> stateByCode = new LinkedHashMap<>();
        for (ChecklistStateEntry entry : parseStateList(json)) {
            if (entry.getCode() != null) {
                stateByCode.put(entry.getCode(), entry);
            }
        }
        return stateByCode;
    }

    private static List<ChecklistStateEntry> parseStateList(String json) {
        if (CommonUtil.isNullOrEmpty(json)) {
            return List.of();
        }
        try {
            List<ChecklistStateEntry> parsed = CommonUtil.getServerMapper()
                    .readValue(json, new TypeReference<List<ChecklistStateEntry>>() {});
            return parsed != null ? parsed : List.of();
        } catch (Exception ex) {
            return List.of();
        }
    }
}
