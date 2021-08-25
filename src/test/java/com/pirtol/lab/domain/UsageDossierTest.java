package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsageDossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsageDossier.class);
        UsageDossier usageDossier1 = new UsageDossier();
        usageDossier1.setId(1L);
        UsageDossier usageDossier2 = new UsageDossier();
        usageDossier2.setId(usageDossier1.getId());
        assertThat(usageDossier1).isEqualTo(usageDossier2);
        usageDossier2.setId(2L);
        assertThat(usageDossier1).isNotEqualTo(usageDossier2);
        usageDossier1.setId(null);
        assertThat(usageDossier1).isNotEqualTo(usageDossier2);
    }
}
