package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArrondissementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArrondissementDTO.class);
        ArrondissementDTO arrondissementDTO1 = new ArrondissementDTO();
        arrondissementDTO1.setId(1L);
        ArrondissementDTO arrondissementDTO2 = new ArrondissementDTO();
        assertThat(arrondissementDTO1).isNotEqualTo(arrondissementDTO2);
        arrondissementDTO2.setId(arrondissementDTO1.getId());
        assertThat(arrondissementDTO1).isEqualTo(arrondissementDTO2);
        arrondissementDTO2.setId(2L);
        assertThat(arrondissementDTO1).isNotEqualTo(arrondissementDTO2);
        arrondissementDTO1.setId(null);
        assertThat(arrondissementDTO1).isNotEqualTo(arrondissementDTO2);
    }
}
