package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RefcadastraleMapperTest {

    private RefcadastraleMapper refcadastraleMapper;

    @BeforeEach
    public void setUp() {
        refcadastraleMapper = new RefcadastraleMapperImpl();
    }
}
