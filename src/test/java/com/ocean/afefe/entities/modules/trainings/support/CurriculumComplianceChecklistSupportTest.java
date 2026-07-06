package com.ocean.afefe.entities.modules.trainings.support;

import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumSubmission;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CurriculumComplianceChecklistSupportTest {

    @Test
    void buildsDefaultUncheckedChecklist() {
        TrainerCurriculumSubmission submission = new TrainerCurriculumSubmission();
        List<CurriculumComplianceChecklistSupport.ChecklistItemView> items =
                CurriculumComplianceChecklistSupport.buildChecklist(submission);
        assertThat(items).isNotEmpty();
        assertThat(items).allMatch(item -> !item.isChecked());
        assertThat(CurriculumComplianceChecklistSupport.computeComplianceScore(items)).isZero();
    }

    @Test
    void mergeAndSerializeUpdatesScore() {
        TrainerCurriculumSubmission submission = new TrainerCurriculumSubmission();
        List<CurriculumComplianceChecklistSupport.ChecklistUpdateRequest> updates = List.of(
                new CurriculumComplianceChecklistSupport.ChecklistUpdateRequest("DOCUMENT_COMPLETE", true),
                new CurriculumComplianceChecklistSupport.ChecklistUpdateRequest("LEARNING_OBJECTIVES_DEFINED", true)
        );
        submission.setComplianceChecklistJson(
                CurriculumComplianceChecklistSupport.mergeAndSerialize(submission, updates, UUID.randomUUID()));
        List<CurriculumComplianceChecklistSupport.ChecklistItemView> items =
                CurriculumComplianceChecklistSupport.buildChecklist(submission);
        int score = CurriculumComplianceChecklistSupport.computeComplianceScore(items);
        assertThat(score).isGreaterThan(0);
        assertThat(items.stream().filter(CurriculumComplianceChecklistSupport.ChecklistItemView::isChecked).count())
                .isEqualTo(2);
    }
}
