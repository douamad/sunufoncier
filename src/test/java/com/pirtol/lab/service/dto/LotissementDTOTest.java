package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LotissementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LotissementDTO.class);
        LotissementDTO lotissementDTO1 = new LotissementDTO();
        lotissementDTO1.setId(1L);
        LotissementDTO lotissementDTO2 = new LotissementDTO();
        assertThat(lotissementDTO1).isNotEqualTo(lotissementDTO2);
        lotissementDTO2.setId(lotissementDTO1.getId());
        assertThat(lotissementDTO1).isEqualTo(lotissementDTO2);
        lotissementDTO2.setId(2L);
        assertThat(lotissementDTO1).isNotEqualTo(lotissementDTO2);
        lotissementDTO1.setId(null);
        assertThat(lotissementDTO1).isNotEqualTo(lotissementDTO2);
    }
}
