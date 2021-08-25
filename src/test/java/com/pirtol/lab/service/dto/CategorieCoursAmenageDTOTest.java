package com.pirtol.lab.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieCoursAmenageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieCoursAmenageDTO.class);
        CategorieCoursAmenageDTO categorieCoursAmenageDTO1 = new CategorieCoursAmenageDTO();
        categorieCoursAmenageDTO1.setId(1L);
        CategorieCoursAmenageDTO categorieCoursAmenageDTO2 = new CategorieCoursAmenageDTO();
        assertThat(categorieCoursAmenageDTO1).isNotEqualTo(categorieCoursAmenageDTO2);
        categorieCoursAmenageDTO2.setId(categorieCoursAmenageDTO1.getId());
        assertThat(categorieCoursAmenageDTO1).isEqualTo(categorieCoursAmenageDTO2);
        categorieCoursAmenageDTO2.setId(2L);
        assertThat(categorieCoursAmenageDTO1).isNotEqualTo(categorieCoursAmenageDTO2);
        categorieCoursAmenageDTO1.setId(null);
        assertThat(categorieCoursAmenageDTO1).isNotEqualTo(categorieCoursAmenageDTO2);
    }
}
