package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RefcadastraleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Refcadastrale.class);
        Refcadastrale refcadastrale1 = new Refcadastrale();
        refcadastrale1.setId(1L);
        Refcadastrale refcadastrale2 = new Refcadastrale();
        refcadastrale2.setId(refcadastrale1.getId());
        assertThat(refcadastrale1).isEqualTo(refcadastrale2);
        refcadastrale2.setId(2L);
        assertThat(refcadastrale1).isNotEqualTo(refcadastrale2);
        refcadastrale1.setId(null);
        assertThat(refcadastrale1).isNotEqualTo(refcadastrale2);
    }
}
