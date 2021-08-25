package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsageDossierDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsageDossierDTO.class);
        UsageDossierDTO usageDossierDTO1 = new UsageDossierDTO();
        usageDossierDTO1.setId(1L);
        UsageDossierDTO usageDossierDTO2 = new UsageDossierDTO();
        assertThat(usageDossierDTO1).isNotEqualTo(usageDossierDTO2);
        usageDossierDTO2.setId(usageDossierDTO1.getId());
        assertThat(usageDossierDTO1).isEqualTo(usageDossierDTO2);
        usageDossierDTO2.setId(2L);
        assertThat(usageDossierDTO1).isNotEqualTo(usageDossierDTO2);
        usageDossierDTO1.setId(null);
        assertThat(usageDossierDTO1).isNotEqualTo(usageDossierDTO2);
    }
}
