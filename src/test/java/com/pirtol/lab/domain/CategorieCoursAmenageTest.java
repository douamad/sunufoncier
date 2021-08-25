package com.pirtol.lab.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pirtol.lab.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieCoursAmenageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieCoursAmenage.class);
        CategorieCoursAmenage categorieCoursAmenage1 = new CategorieCoursAmenage();
        categorieCoursAmenage1.setId(1L);
        CategorieCoursAmenage categorieCoursAmenage2 = new CategorieCoursAmenage();
        categorieCoursAmenage2.setId(categorieCoursAmenage1.getId());
        assertThat(categorieCoursAmenage1).isEqualTo(categorieCoursAmenage2);
        categorieCoursAmenage2.setId(2L);
        assertThat(categorieCoursAmenage1).isNotEqualTo(categorieCoursAmenage2);
        categorieCoursAmenage1.setId(null);
        assertThat(categorieCoursAmenage1).isNotEqualTo(categorieCoursAmenage2);
    }
}
