package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LotissementMapperTest {

    private LotissementMapper lotissementMapper;

    @BeforeEach
    public void setUp() {
        lotissementMapper = new LotissementMapperImpl();
    }
}
