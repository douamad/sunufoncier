package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieClotureMapperTest {

    private CategorieClotureMapper categorieClotureMapper;

    @BeforeEach
    public void setUp() {
        categorieClotureMapper = new CategorieClotureMapperImpl();
    }
}
