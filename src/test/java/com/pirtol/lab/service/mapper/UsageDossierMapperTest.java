package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsageDossierMapperTest {

    private UsageDossierMapper usageDossierMapper;

    @BeforeEach
    public void setUp() {
        usageDossierMapper = new UsageDossierMapperImpl();
    }
}
