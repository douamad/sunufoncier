package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationBatimentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationBatimentsDTO.class);
        EvaluationBatimentsDTO evaluationBatimentsDTO1 = new EvaluationBatimentsDTO();
        evaluationBatimentsDTO1.setId(1L);
        EvaluationBatimentsDTO evaluationBatimentsDTO2 = new EvaluationBatimentsDTO();
        assertThat(evaluationBatimentsDTO1).isNotEqualTo(evaluationBatimentsDTO2);
        evaluationBatimentsDTO2.setId(evaluationBatimentsDTO1.getId());
        assertThat(evaluationBatimentsDTO1).isEqualTo(evaluationBatimentsDTO2);
        evaluationBatimentsDTO2.setId(2L);
        assertThat(evaluationBatimentsDTO1).isNotEqualTo(evaluationBatimentsDTO2);
        evaluationBatimentsDTO1.setId(null);
        assertThat(evaluationBatimentsDTO1).isNotEqualTo(evaluationBatimentsDTO2);
    }
}
