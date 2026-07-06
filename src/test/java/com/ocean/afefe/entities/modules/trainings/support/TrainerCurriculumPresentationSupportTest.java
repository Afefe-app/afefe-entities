package com.ocean.afefe.entities.modules.trainings.support;

import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TrainerCurriculumPresentationSupportTest {

    @Test
    void parseStringListReturnsEmptyForNull() {
        assertThat(TrainerCurriculumPresentationSupport.parseStringList(null)).isEmpty();
    }

    @Test
    void parseStringListParsesJsonArray() {
        List<String> values = TrainerCurriculumPresentationSupport.parseStringList("[\"A\",\"B\"]");
        assertThat(values).containsExactly("A", "B");
    }

    @Test
    void buildWorkflowMarksCurrentStepForFeedback() {
        var steps = TrainerCurriculumPresentationSupport.buildWorkflow(TrainerCurriculumStatus.FEEDBACK);
        assertThat(steps).hasSize(4);
        assertThat(steps.get(2).getState()).isEqualTo("CURRENT");
        assertThat(steps.get(3).getState()).isEqualTo("PENDING");
    }
}
