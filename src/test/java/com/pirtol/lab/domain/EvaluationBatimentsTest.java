package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationBatimentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationBatiments.class);
        EvaluationBatiments evaluationBatiments1 = new EvaluationBatiments();
        evaluationBatiments1.setId(1L);
        EvaluationBatiments evaluationBatiments2 = new EvaluationBatiments();
        evaluationBatiments2.setId(evaluationBatiments1.getId());
        assertThat(evaluationBatiments1).isEqualTo(evaluationBatiments2);
        evaluationBatiments2.setId(2L);
        assertThat(evaluationBatiments1).isNotEqualTo(evaluationBatiments2);
        evaluationBatiments1.setId(null);
        assertThat(evaluationBatiments1).isNotEqualTo(evaluationBatiments2);
    }
}
