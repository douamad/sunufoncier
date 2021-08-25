package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationClotureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationClotureDTO.class);
        EvaluationClotureDTO evaluationClotureDTO1 = new EvaluationClotureDTO();
        evaluationClotureDTO1.setId(1L);
        EvaluationClotureDTO evaluationClotureDTO2 = new EvaluationClotureDTO();
        assertThat(evaluationClotureDTO1).isNotEqualTo(evaluationClotureDTO2);
        evaluationClotureDTO2.setId(evaluationClotureDTO1.getId());
        assertThat(evaluationClotureDTO1).isEqualTo(evaluationClotureDTO2);
        evaluationClotureDTO2.setId(2L);
        assertThat(evaluationClotureDTO1).isNotEqualTo(evaluationClotureDTO2);
        evaluationClotureDTO1.setId(null);
        assertThat(evaluationClotureDTO1).isNotEqualTo(evaluationClotureDTO2);
    }
}
