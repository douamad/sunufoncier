package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluationCoursAmenageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationCoursAmenage.class);
        EvaluationCoursAmenage evaluationCoursAmenage1 = new EvaluationCoursAmenage();
        evaluationCoursAmenage1.setId(1L);
        EvaluationCoursAmenage evaluationCoursAmenage2 = new EvaluationCoursAmenage();
        evaluationCoursAmenage2.setId(evaluationCoursAmenage1.getId());
        assertThat(evaluationCoursAmenage1).isEqualTo(evaluationCoursAmenage2);
        evaluationCoursAmenage2.setId(2L);
        assertThat(evaluationCoursAmenage1).isNotEqualTo(evaluationCoursAmenage2);
        evaluationCoursAmenage1.setId(null);
        assertThat(evaluationCoursAmenage1).isNotEqualTo(evaluationCoursAmenage2);
    }
}
