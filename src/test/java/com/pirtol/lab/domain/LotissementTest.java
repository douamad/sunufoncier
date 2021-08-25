package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LotissementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lotissement.class);
        Lotissement lotissement1 = new Lotissement();
        lotissement1.setId(1L);
        Lotissement lotissement2 = new Lotissement();
        lotissement2.setId(lotissement1.getId());
        assertThat(lotissement1).isEqualTo(lotissement2);
        lotissement2.setId(2L);
        assertThat(lotissement1).isNotEqualTo(lotissement2);
        lotissement1.setId(null);
        assertThat(lotissement1).isNotEqualTo(lotissement2);
    }
}
