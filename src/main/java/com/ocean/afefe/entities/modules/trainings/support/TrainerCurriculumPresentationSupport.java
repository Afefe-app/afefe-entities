package com.ocean.afefe.entities.modules.trainings.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumStatus;
import com.ocean.afefe.entities.modules.trainings.view.TrainerCurriculumViews.MonthView;
import com.ocean.afefe.entities.modules.trainings.view.TrainerCurriculumViews.WorkflowStepView;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public final class TrainerCurriculumPresentationSupport {

    private TrainerCurriculumPresentationSupport() {
    }

    public static List<String> parseStringList(String jsonList) {
        if (CommonUtil.isNullOrEmpty(jsonList)) {
            return new ArrayList<>();
        }
        try {
            List<String> parsed = CommonUtil.getServerMapper().readValue(jsonList, new TypeReference<List<String>>() {});
            return parsed != null ? parsed : new ArrayList<>();
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static List<MonthView> parseMonths(String jsonList) {
        if (CommonUtil.isNullOrEmpty(jsonList)) {
            return new ArrayList<>();
        }
        try {
            List<MonthView> parsed = CommonUtil.getServerMapper().readValue(jsonList, new TypeReference<List<MonthView>>() {});
            return parsed != null ? parsed : new ArrayList<>();
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public static List<WorkflowStepView> buildWorkflow(TrainerCurriculumStatus status) {
        int current = resolveCurrentStep(status);
        List<WorkflowStepView> steps = new ArrayList<>();
        steps.add(WorkflowStepView.builder().position(1).label("Submitted").state(resolveStepState(current, 1)).build());
        steps.add(WorkflowStepView.builder().position(2).label("Under Review").state(resolveStepState(current, 2)).build());
        steps.add(WorkflowStepView.builder().position(3).label("Feedback").state(resolveStepState(current, 3)).build());
        steps.add(WorkflowStepView.builder().position(4).label("Approved").state(resolveStepState(current, 4)).build());
        return steps;
    }

    private static int resolveCurrentStep(TrainerCurriculumStatus status) {
        if (status == null) {
            return 1;
        }
        return switch (status) {
            case SUBMITTED -> 1;
            case UNDER_REVIEW, FLAGGED -> 2;
            case FEEDBACK, REJECTED -> 3;
            case APPROVED -> 4;
        };
    }

    private static String resolveStepState(int current, int position) {
        if (position < current) {
            return "COMPLETED";
        }
        if (position == current) {
            return "CURRENT";
        }
        return "PENDING";
    }
}
