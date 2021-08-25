package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RefParcelaireDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefParcelaireDTO.class);
        RefParcelaireDTO refParcelaireDTO1 = new RefParcelaireDTO();
        refParcelaireDTO1.setId(1L);
        RefParcelaireDTO refParcelaireDTO2 = new RefParcelaireDTO();
        assertThat(refParcelaireDTO1).isNotEqualTo(refParcelaireDTO2);
        refParcelaireDTO2.setId(refParcelaireDTO1.getId());
        assertThat(refParcelaireDTO1).isEqualTo(refParcelaireDTO2);
        refParcelaireDTO2.setId(2L);
        assertThat(refParcelaireDTO1).isNotEqualTo(refParcelaireDTO2);
        refParcelaireDTO1.setId(null);
        assertThat(refParcelaireDTO1).isNotEqualTo(refParcelaireDTO2);
    }
}
