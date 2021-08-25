package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationSurfaceBatieDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationSurfaceBatieDTO.class);
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO1 = new EvaluationSurfaceBatieDTO();
        evaluationSurfaceBatieDTO1.setId(1L);
        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO2 = new EvaluationSurfaceBatieDTO();
        assertThat(evaluationSurfaceBatieDTO1).isNotEqualTo(evaluationSurfaceBatieDTO2);
        evaluationSurfaceBatieDTO2.setId(evaluationSurfaceBatieDTO1.getId());
        assertThat(evaluationSurfaceBatieDTO1).isEqualTo(evaluationSurfaceBatieDTO2);
        evaluationSurfaceBatieDTO2.setId(2L);
        assertThat(evaluationSurfaceBatieDTO1).isNotEqualTo(evaluationSurfaceBatieDTO2);
        evaluationSurfaceBatieDTO1.setId(null);
        assertThat(evaluationSurfaceBatieDTO1).isNotEqualTo(evaluationSurfaceBatieDTO2);
    }
}
