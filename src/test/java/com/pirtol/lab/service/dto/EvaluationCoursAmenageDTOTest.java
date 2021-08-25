package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationCoursAmenageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationCoursAmenageDTO.class);
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO1 = new EvaluationCoursAmenageDTO();
        evaluationCoursAmenageDTO1.setId(1L);
        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO2 = new EvaluationCoursAmenageDTO();
        assertThat(evaluationCoursAmenageDTO1).isNotEqualTo(evaluationCoursAmenageDTO2);
        evaluationCoursAmenageDTO2.setId(evaluationCoursAmenageDTO1.getId());
        assertThat(evaluationCoursAmenageDTO1).isEqualTo(evaluationCoursAmenageDTO2);
        evaluationCoursAmenageDTO2.setId(2L);
        assertThat(evaluationCoursAmenageDTO1).isNotEqualTo(evaluationCoursAmenageDTO2);
        evaluationCoursAmenageDTO1.setId(null);
        assertThat(evaluationCoursAmenageDTO1).isNotEqualTo(evaluationCoursAmenageDTO2);
    }
}
