package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieCoursAmenageMapperTest {

    private CategorieCoursAmenageMapper categorieCoursAmenageMapper;

    @BeforeEach
    public void setUp() {
        categorieCoursAmenageMapper = new CategorieCoursAmenageMapperImpl();
    }
}
