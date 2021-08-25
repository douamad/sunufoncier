package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureDTO.class);
        NatureDTO natureDTO1 = new NatureDTO();
        natureDTO1.setId(1L);
        NatureDTO natureDTO2 = new NatureDTO();
        assertThat(natureDTO1).isNotEqualTo(natureDTO2);
        natureDTO2.setId(natureDTO1.getId());
        assertThat(natureDTO1).isEqualTo(natureDTO2);
        natureDTO2.setId(2L);
        assertThat(natureDTO1).isNotEqualTo(natureDTO2);
        natureDTO1.setId(null);
        assertThat(natureDTO1).isNotEqualTo(natureDTO2);
    }
}
