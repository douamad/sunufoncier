package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RefParcelaireMapperTest {

    private RefParcelaireMapper refParcelaireMapper;

    @BeforeEach
    public void setUp() {
        refParcelaireMapper = new RefParcelaireMapperImpl();
    }
}
