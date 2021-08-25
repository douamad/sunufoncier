package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepresentantMapperTest {

    private RepresentantMapper representantMapper;

    @BeforeEach
    public void setUp() {
        representantMapper = new RepresentantMapperImpl();
    }
}
