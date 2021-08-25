package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieNatureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieNatureDTO.class);
        CategorieNatureDTO categorieNatureDTO1 = new CategorieNatureDTO();
        categorieNatureDTO1.setId(1L);
        CategorieNatureDTO categorieNatureDTO2 = new CategorieNatureDTO();
        assertThat(categorieNatureDTO1).isNotEqualTo(categorieNatureDTO2);
        categorieNatureDTO2.setId(categorieNatureDTO1.getId());
        assertThat(categorieNatureDTO1).isEqualTo(categorieNatureDTO2);
        categorieNatureDTO2.setId(2L);
        assertThat(categorieNatureDTO1).isNotEqualTo(categorieNatureDTO2);
        categorieNatureDTO1.setId(null);
        assertThat(categorieNatureDTO1).isNotEqualTo(categorieNatureDTO2);
    }
}
