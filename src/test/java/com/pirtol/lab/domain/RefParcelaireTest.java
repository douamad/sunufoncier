package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RefParcelaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefParcelaire.class);
        RefParcelaire refParcelaire1 = new RefParcelaire();
        refParcelaire1.setId(1L);
        RefParcelaire refParcelaire2 = new RefParcelaire();
        refParcelaire2.setId(refParcelaire1.getId());
        assertThat(refParcelaire1).isEqualTo(refParcelaire2);
        refParcelaire2.setId(2L);
        assertThat(refParcelaire1).isNotEqualTo(refParcelaire2);
        refParcelaire1.setId(null);
        assertThat(refParcelaire1).isNotEqualTo(refParcelaire2);
    }
}
