package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepresentantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepresentantDTO.class);
        RepresentantDTO representantDTO1 = new RepresentantDTO();
        representantDTO1.setId(1L);
        RepresentantDTO representantDTO2 = new RepresentantDTO();
        assertThat(representantDTO1).isNotEqualTo(representantDTO2);
        representantDTO2.setId(representantDTO1.getId());
        assertThat(representantDTO1).isEqualTo(representantDTO2);
        representantDTO2.setId(2L);
        assertThat(representantDTO1).isNotEqualTo(representantDTO2);
        representantDTO1.setId(null);
        assertThat(representantDTO1).isNotEqualTo(representantDTO2);
    }
}
