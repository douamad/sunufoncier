package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepresentantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Representant.class);
        Representant representant1 = new Representant();
        representant1.setId(1L);
        Representant representant2 = new Representant();
        representant2.setId(representant1.getId());
        assertThat(representant1).isEqualTo(representant2);
        representant2.setId(2L);
        assertThat(representant1).isNotEqualTo(representant2);
        representant1.setId(null);
        assertThat(representant1).isNotEqualTo(representant2);
    }
}
