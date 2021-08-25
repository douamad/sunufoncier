package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NatureMapperTest {

    private NatureMapper natureMapper;

    @BeforeEach
    public void setUp() {
        natureMapper = new NatureMapperImpl();
    }
}
