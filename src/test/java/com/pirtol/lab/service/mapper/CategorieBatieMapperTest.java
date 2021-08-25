package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieBatieMapperTest {

    private CategorieBatieMapper categorieBatieMapper;

    @BeforeEach
    public void setUp() {
        categorieBatieMapper = new CategorieBatieMapperImpl();
    }
}
