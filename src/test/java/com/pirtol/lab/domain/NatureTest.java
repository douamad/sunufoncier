package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nature.class);
        Nature nature1 = new Nature();
        nature1.setId(1L);
        Nature nature2 = new Nature();
        nature2.setId(nature1.getId());
        assertThat(nature1).isEqualTo(nature2);
        nature2.setId(2L);
        assertThat(nature1).isNotEqualTo(nature2);
        nature1.setId(null);
        assertThat(nature1).isNotEqualTo(nature2);
    }
}
