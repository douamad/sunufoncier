package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RefcadastraleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefcadastraleDTO.class);
        RefcadastraleDTO refcadastraleDTO1 = new RefcadastraleDTO();
        refcadastraleDTO1.setId(1L);
        RefcadastraleDTO refcadastraleDTO2 = new RefcadastraleDTO();
        assertThat(refcadastraleDTO1).isNotEqualTo(refcadastraleDTO2);
        refcadastraleDTO2.setId(refcadastraleDTO1.getId());
        assertThat(refcadastraleDTO1).isEqualTo(refcadastraleDTO2);
        refcadastraleDTO2.setId(2L);
        assertThat(refcadastraleDTO1).isNotEqualTo(refcadastraleDTO2);
        refcadastraleDTO1.setId(null);
        assertThat(refcadastraleDTO1).isNotEqualTo(refcadastraleDTO2);
    }
}
