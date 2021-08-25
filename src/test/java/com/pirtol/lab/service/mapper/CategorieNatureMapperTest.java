package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieNatureMapperTest {

    private CategorieNatureMapper categorieNatureMapper;

    @BeforeEach
    public void setUp() {
        categorieNatureMapper = new CategorieNatureMapperImpl();
    }
}
