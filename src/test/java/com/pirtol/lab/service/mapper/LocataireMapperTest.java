package com.pirtol.lab.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocataireMapperTest {

    private LocataireMapper locataireMapper;

    @BeforeEach
    public void setUp() {
        locataireMapper = new LocataireMapperImpl();
    }
}
