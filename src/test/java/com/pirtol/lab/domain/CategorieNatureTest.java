package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieNatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieNature.class);
        CategorieNature categorieNature1 = new CategorieNature();
        categorieNature1.setId(1L);
        CategorieNature categorieNature2 = new CategorieNature();
        categorieNature2.setId(categorieNature1.getId());
        assertThat(categorieNature1).isEqualTo(categorieNature2);
        categorieNature2.setId(2L);
        assertThat(categorieNature1).isNotEqualTo(categorieNature2);
        categorieNature1.setId(null);
        assertThat(categorieNature1).isNotEqualTo(categorieNature2);
    }
}
