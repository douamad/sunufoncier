package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArrondissementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arrondissement.class);
        Arrondissement arrondissement1 = new Arrondissement();
        arrondissement1.setId(1L);
        Arrondissement arrondissement2 = new Arrondissement();
        arrondissement2.setId(arrondissement1.getId());
        assertThat(arrondissement1).isEqualTo(arrondissement2);
        arrondissement2.setId(2L);
        assertThat(arrondissement1).isNotEqualTo(arrondissement2);
        arrondissement1.setId(null);
        assertThat(arrondissement1).isNotEqualTo(arrondissement2);
    }
}
