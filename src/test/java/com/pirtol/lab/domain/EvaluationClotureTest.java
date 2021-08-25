package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationClotureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationCloture.class);
        EvaluationCloture evaluationCloture1 = new EvaluationCloture();
        evaluationCloture1.setId(1L);
        EvaluationCloture evaluationCloture2 = new EvaluationCloture();
        evaluationCloture2.setId(evaluationCloture1.getId());
        assertThat(evaluationCloture1).isEqualTo(evaluationCloture2);
        evaluationCloture2.setId(2L);
        assertThat(evaluationCloture1).isNotEqualTo(evaluationCloture2);
        evaluationCloture1.setId(null);
        assertThat(evaluationCloture1).isNotEqualTo(evaluationCloture2);
    }
}
