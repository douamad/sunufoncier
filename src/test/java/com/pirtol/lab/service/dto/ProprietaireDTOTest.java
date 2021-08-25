package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProprietaireDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProprietaireDTO.class);
        ProprietaireDTO proprietaireDTO1 = new ProprietaireDTO();
        proprietaireDTO1.setId(1L);
        ProprietaireDTO proprietaireDTO2 = new ProprietaireDTO();
        assertThat(proprietaireDTO1).isNotEqualTo(proprietaireDTO2);
        proprietaireDTO2.setId(proprietaireDTO1.getId());
        assertThat(proprietaireDTO1).isEqualTo(proprietaireDTO2);
        proprietaireDTO2.setId(2L);
        assertThat(proprietaireDTO1).isNotEqualTo(proprietaireDTO2);
        proprietaireDTO1.setId(null);
        assertThat(proprietaireDTO1).isNotEqualTo(proprietaireDTO2);
    }
}
