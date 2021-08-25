package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrondissementMapperTest {

    private ArrondissementMapper arrondissementMapper;

    @BeforeEach
    public void setUp() {
        arrondissementMapper = new ArrondissementMapperImpl();
    }
}
