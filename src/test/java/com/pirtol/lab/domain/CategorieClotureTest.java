package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieClotureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieCloture.class);
        CategorieCloture categorieCloture1 = new CategorieCloture();
        categorieCloture1.setId(1L);
        CategorieCloture categorieCloture2 = new CategorieCloture();
        categorieCloture2.setId(categorieCloture1.getId());
        assertThat(categorieCloture1).isEqualTo(categorieCloture2);
        categorieCloture2.setId(2L);
        assertThat(categorieCloture1).isNotEqualTo(categorieCloture2);
        categorieCloture1.setId(null);
        assertThat(categorieCloture1).isNotEqualTo(categorieCloture2);
    }
}
