package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationSurfaceBatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationSurfaceBatie.class);
        EvaluationSurfaceBatie evaluationSurfaceBatie1 = new EvaluationSurfaceBatie();
        evaluationSurfaceBatie1.setId(1L);
        EvaluationSurfaceBatie evaluationSurfaceBatie2 = new EvaluationSurfaceBatie();
        evaluationSurfaceBatie2.setId(evaluationSurfaceBatie1.getId());
        assertThat(evaluationSurfaceBatie1).isEqualTo(evaluationSurfaceBatie2);
        evaluationSurfaceBatie2.setId(2L);
        assertThat(evaluationSurfaceBatie1).isNotEqualTo(evaluationSurfaceBatie2);
        evaluationSurfaceBatie1.setId(null);
        assertThat(evaluationSurfaceBatie1).isNotEqualTo(evaluationSurfaceBatie2);
    }
}
