package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProprietaireMapperTest {

    private ProprietaireMapper proprietaireMapper;

    @BeforeEach
    public void setUp() {
        proprietaireMapper = new ProprietaireMapperImpl();
    }
}
