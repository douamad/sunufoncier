package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommuneMapperTest {

    private CommuneMapper communeMapper;

    @BeforeEach
    public void setUp() {
        communeMapper = new CommuneMapperImpl();
    }
}
