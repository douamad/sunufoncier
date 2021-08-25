package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieBatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieBatie.class);
        CategorieBatie categorieBatie1 = new CategorieBatie();
        categorieBatie1.setId(1L);
        CategorieBatie categorieBatie2 = new CategorieBatie();
        categorieBatie2.setId(categorieBatie1.getId());
        assertThat(categorieBatie1).isEqualTo(categorieBatie2);
        categorieBatie2.setId(2L);
        assertThat(categorieBatie1).isNotEqualTo(categorieBatie2);
        categorieBatie1.setId(null);
        assertThat(categorieBatie1).isNotEqualTo(categorieBatie2);
    }
}
